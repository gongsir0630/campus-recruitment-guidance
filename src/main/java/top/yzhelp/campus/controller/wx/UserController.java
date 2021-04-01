package top.yzhelp.campus.controller.wx;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yzhelp.campus.controller.res.CodeMsg;
import top.yzhelp.campus.controller.res.Result;
import top.yzhelp.campus.service.WxUserService;
import top.yzhelp.campus.shiro.ShiroRealm;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="https://github.com/gongsir0630">码之泪殇</a>
 * @date 2021/3/29 21:04
 * 你的指尖,拥有改变世界的力量
 * @description 小程序用户接口
 */
@RestController
@Slf4j
@RequestMapping("wx/user")
@Api(tags = "小程序用户接口")
public class UserController {

  @Resource
  private WxUserService userService;

  /**
   * 从认证信息中获取用户 openId
   * @return openId
   */
  private String getOpenId() {
    return ShiroRealm.getShiroAccount().getAuthName();
  }

  /**
   * 小程序用户登录接口: 通过js_code换取openId, 判断用户是否已经注册
   * @param code wx.login() 得到的code凭证
   * @return token
   */
  @PostMapping("/login")
  @ApiOperation("用户登录")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "code",value = "小程序的 js_code",required = true)
  })
  @ApiResponses({
    @ApiResponse(code = 200,message = "接口调用成功"),
    @ApiResponse(code = 401,message = "登录信息异常,请检查 token 是否有效")
  })
  public ResponseEntity<Result<Map<String,Object>>> login(String code) {
    if (StringUtils.isBlank(code)) {
      return new ResponseEntity<>(Result.fail(new CodeMsg(401,"code is empty"), null), HttpStatus.OK);
    }
    log.info("--->接收到来自小程序端的code:[{}]",code);

    Map<String, String> loginMap = userService.login(code);
    boolean canLogin = Boolean.parseBoolean(loginMap.get("canLogin"));
    String token = loginMap.get("token");
    Map<String,Object> data = new HashMap<>();
    data.put("token",token);
    data.put("canLogin",canLogin);
    log.info("--->>>返回认证信息:[{}]", data.toString());
    if (!canLogin) {
      // todo: 用户不存在,提示用户注册
      return new ResponseEntity<>(Result.fail(CodeMsg.NO_USER,data),HttpStatus.OK);
    }
    return new ResponseEntity<>(Result.success(data),HttpStatus.OK);
  }

  /**
   * 使用 RequiresAuthentication 注解, 需要验证才能访问
   * @return userId
   */
  @GetMapping("/hello")
  @RequiresRoles("wx")
  public ResponseEntity<Result<Map<String,String>>> requireAuth() {
    Map<String,String> data = new HashMap<>();
    data.put("hello",getOpenId());
    return new ResponseEntity<>(Result.success(data),HttpStatus.OK);
  }
}
