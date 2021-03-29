package top.yzhelp.campus.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import top.yzhelp.campus.shiro.vo.ShiroAccount;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="https://github.com/gongsir0630">码之泪殇</a>
 * @date 2021/3/29 13:13
 * 你的指尖,拥有改变世界的力量
 * @description jwt工具类: 生成token签名, token校验
 */
@Component
@SuppressWarnings("All")
public class JwtUtil {
  /**
   * 过期时间: 2小时
   */
  private static final long EXPIRE_TIME = 7200;
  /**
   * 使用 appid 签名
   */
  @Value("${wx.appid}")
  private String appsecret;

  /**
   * 角色信息
   */
  public static final String ROLE_ADMIN = "admin";
  public static final String ROLE_WX = "wx";

  @Autowired
  private StringRedisTemplate redisTemplate;

  /**
   * 根据微信用户登陆信息创建 token
   * 使用`uuid`随机生成一个jwt-id
   * 将用户的`openId`、`session_key`连同`jwt-id`一起，使用小程序的`appid`进行签名加密并设置过期时间，最终生成`token`
   * 将`"JWT-SESSION-"+jwt-id`和`token`以key-value的形式存入`redis`中，并设置相同的过期时间
   * 注 : 这里的token会被缓存到redis中,用作为二次验证
   * redis里面缓存的时间应该和jwt token的过期时间设置相同
   *
   * @param account 微信用户信息
   * @return 返回 jwt token
   */
  public String sign(ShiroAccount account) {
    //JWT 随机ID,做为redis验证的key
    String jwtId = UUID.randomUUID().toString();
    //1 . 加密算法进行签名得到token
    Algorithm algorithm = Algorithm.HMAC256(appsecret);
    String token = JWT.create()
      .withClaim("name", account.getAuthName())
      .withClaim("secret", account.getAuthSecret())
      .withClaim("role",String.join(",",account.getRole()))
      .withClaim("jwt-id",jwtId)
      .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRE_TIME * 1000))
      .sign(algorithm);
    //2 . Redis缓存JWT, 注 : 请和JWT过期时间一致
    redisTemplate.opsForValue().set("JWT-SESSION-"+jwtId, token, EXPIRE_TIME, TimeUnit.SECONDS);
    return token;
  }

  /**
   * token 检验
   * @param token
   * @return bool
   */
  public boolean verify(String token) {
    try {
      //1 . 根据token解密，解密出jwt-id , 先从redis中查找出redisToken，匹配是否相同
      String id = getJwtId(token);
      String redisToken = redisTemplate.opsForValue().get("JWT-SESSION-" + id);
      if (!token.equals(redisToken)) {
        return Boolean.FALSE;
      }
      //2 . 得到算法相同的JWTVerifier
      Algorithm algorithm = Algorithm.HMAC256(appsecret);
      JWTVerifier verifier = JWT.require(algorithm)
        .withClaim("name", getAuthName(redisToken))
        .withClaim("secret", getAuthSecret(redisToken))
        .withClaim("role", getRole(redisToken))
        .withClaim("jwt-id",id)
        .build();
      //3 . 验证token
      verifier.verify(token);
      //4 . Redis缓存JWT续期
      redisTemplate.opsForValue().set("JWT-SESSION-" + getClaimsByToken(token).get("jwt-id").asString(),
        redisToken,
        EXPIRE_TIME,
        TimeUnit.SECONDS);
      return Boolean.TRUE;
    } catch (Exception e) {
      //捕捉到任何异常都视为校验失败
      return Boolean.FALSE;
    }
  }

  /**
   * 从token解密信息
   * @param token token
   * @return
   * @throws JWTDecodeException
   */
  public Map<String, Claim> getClaimsByToken(String token) throws JWTDecodeException {
    return JWT.decode(token).getClaims();
  }

  /**
   * 获取 AuthName
   * @param token
   * @return
   * @throws JWTDecodeException
   */
  public String getAuthName(String token) throws JWTDecodeException {
    return getClaimsByToken(token).get("name").asString();
  }

  /**
   * 获取 AuthSecret
   * @param token
   * @return
   * @throws JWTDecodeException
   */
  public String getAuthSecret(String token) throws JWTDecodeException{
    return getClaimsByToken(token).get("secret").asString();
  }

  /**
   * 获取 Role
   * @param token
   * @return
   * @throws JWTDecodeException
   */
  public String getRole(String token) throws JWTDecodeException{
    return getClaimsByToken(token).get("role").asString();
  }

  /**
   * 获取 jwt-id
   * @param token
   * @return
   * @throws JWTDecodeException
   */
  public String getJwtId(String token) throws JWTDecodeException{
    return getClaimsByToken(token).get("jwt-id").asString();
  }
}

