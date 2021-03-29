package top.yzhelp.campus.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;
import top.yzhelp.campus.controller.res.CodeMsg;
import top.yzhelp.campus.exception.ApiAuthException;
import top.yzhelp.campus.shiro.vo.JwtToken;
import top.yzhelp.campus.shiro.vo.ShiroAccount;
import top.yzhelp.campus.util.JwtUtil;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author <a href="https://github.com/gongsir0630">码之泪殇</a>
 * @date 2021/3/29 14:13
 * 你的指尖,拥有改变世界的力量
 * @description Realm 的一个配置管理类 allRealm()方法得到所有的realm
 */
@Component
@Slf4j
public class ShiroRealm {

  @Resource
  private JwtUtil jwtUtil;

  public static ShiroAccount getShiroAccount() {
    return (ShiroAccount) SecurityUtils.getSubject().getPrincipal();
  }

  /**
   * 封装所有自定义的realm规则链 -> shiro配置中会将规则注入到shiro的securityManager
   * @return 所有自定义的realm规则
   */
  public List<Realm> allRealm() {
    List<Realm> realmList = new LinkedList<>();
    realmList.add(authorizingRealm());
    return Collections.unmodifiableList(realmList);
  }

  private AuthorizingRealm authorizingRealm() {
    AuthorizingRealm realm = new AuthorizingRealm() {
      /**
       * 注意坑点: 必须重写此方法，不然Shiro会报错
       * 因为创建了 JWTToken 用于替换Shiro原生 token,所以必须在此方法中显式的进行替换，否则在进行判断时会一直失败
       */
      @Override
      public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
      }

      /**
       * 鉴权
       * @param principalCollection 用户信息
       * @return 鉴权信息
       */
      @Override
      protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        ShiroAccount account = (ShiroAccount) principalCollection.getPrimaryPrincipal();
        // 添加权限
        info.addRoles(account.getRole());
        return info;
      }

      /**
       * 认证
       * @param authenticationToken token
       * @return 认证信息
       * @throws AuthenticationException 异常信息
       */
      @Override
      protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        final String jwtToken = (String) authenticationToken.getPrincipal();
        if (!jwtUtil.verify(jwtToken)) {
          throw new ApiAuthException(CodeMsg.TOKEN_ERROR);
        }
        // 角色信息
        final String role = jwtUtil.getRole(jwtToken);
        // 用户名
        final String authName = jwtUtil.getAuthName(jwtToken);
        // 封装认证信息
        ShiroAccount account = new ShiroAccount();
        account.setAuthName(authName);
        account.setAuthSecret(jwtToken);
        account.setRole(Arrays.asList(role.split(",")));
        return new SimpleAuthenticationInfo(account,jwtToken,getName());
      }
    };
    // 密码校验, 这里因为是JWT形式,就无需密码校验和加密,直接让其返回为true(如果不设置的话,该值默认为false,即始终验证不通过)
    realm.setCredentialsMatcher((authenticationToken,authenticationInfo) -> true);
    return realm;
  }
}
