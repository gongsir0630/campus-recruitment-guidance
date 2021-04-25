package top.yzhelp.campus.controller.admin;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yzhelp.campus.controller.res.CodeMsg;
import top.yzhelp.campus.controller.res.Result;
import top.yzhelp.campus.service.AdminUserService;
import top.yzhelp.campus.shiro.ShiroRealm;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author <a href="https://github.com/gongsir0630">码之泪殇</a>
 * @date 2021/4/25 11:20
 * 你的指尖,拥有改变世界的力量
 * @description description
 */
@RestController
@Slf4j
@RequestMapping("admin/user")
@Api(tags = "ADMIN-管理员用户接口")
public class AdminUserController {
  @Resource
  AdminUserService adminUserService;

  /**
   * 从认证信息中获取用户 openId
   * @return openId
   */
  private String getOpenId() {
    return ShiroRealm.getShiroAccount().getAuthName();
  }

  @PostMapping("/loginByTel")
  @ApiOperation("手机验证登陆")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "tel",value = "手机号",required = true),
    @ApiImplicitParam(name = "code",value = "验证码",required = true)
  })
  @ApiResponses({
    @ApiResponse(code = 200,message = "接口调用成功"),
    @ApiResponse(code = 401,message = "登录信息异常,请检查 token 是否有效")
  })
  public ResponseEntity<Result<?>> loginBySms(String tel,String code) {
    return null;
  }

  @PostMapping("/loginByPass")
  @ApiOperation("管理登录")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "userId",value = "用户名/手机号:测试账号(201731061426/17361040630)",required = true),
    @ApiImplicitParam(name = "password",value = "密码:测试密码(201731061426)",required = true)
  })
  @ApiResponses({
    @ApiResponse(code = 200,message = "接口调用成功"),
    @ApiResponse(code = 401,message = "登录信息异常,请检查 token 是否有效")
  })
  public ResponseEntity<Result<?>> loginByPassword(String userId,String password) {
    if (StringUtils.isBlank(userId) || StringUtils.isBlank(password)) {
      return new ResponseEntity<>(Result.fail(new CodeMsg(401,"用户名或密码不能为空"), null), HttpStatus.OK);
    }
    log.info("--->接收到来自Web端的登录请求:[userId: {}, password: {}]",userId, password);

    Map<String, String> loginMap = this.adminUserService.loginByPass(userId, password);
    String token = loginMap.get("token");
    boolean canLogin = Boolean.parseBoolean(loginMap.get("canLogin"));
    var data = MapUtil.newHashMap();
    data.put("token",token);
    data.put("canLogin",canLogin);
    log.info("--->>>返回认证信息:[{}]", data.toString());
    if (!canLogin) {
      // todo: 用户不存在,提示用户注册
      return new ResponseEntity<>(Result.fail(CodeMsg.NO_USER,data),HttpStatus.OK);
    }
    data.put("userInfo",this.adminUserService.getByUserId(userId));
    return new ResponseEntity<>(Result.success(data),HttpStatus.OK);
  }
}
