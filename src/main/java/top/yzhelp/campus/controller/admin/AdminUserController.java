package top.yzhelp.campus.controller.admin;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import cn.hutool.core.map.MapUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import top.yzhelp.campus.controller.AbstractCrgController;
import top.yzhelp.campus.enums.WebResultCode;
import top.yzhelp.campus.exception.ApiAuthException;
import top.yzhelp.campus.model.common.Message;
import top.yzhelp.campus.service.AdminUserService;

/**
 * @author <a href="https://github.com/gongsir0630">Kyle</a>
 * @date 2021/4/25 11:20
 * 你的指尖,拥有改变世界的力量
 * @description description
 */
@RestController
@Slf4j
@RequestMapping("admin/user")
@Api(tags = "ADMIN-管理员用户接口")
public class AdminUserController extends AbstractCrgController {
    @Resource
    private AdminUserService adminUserService;

    @GetMapping("mine")
    @RequiresRoles("admin")
    public ResponseEntity<Message<?>> getProfile() {
        return new ResponseEntity<>(Message.success(this.adminUserService.getByUserId(getOpenId())), HttpStatus.OK);
    }

    @GetMapping("list")
    @RequiresRoles("admin")
    public ResponseEntity<Message<?>> getList() {
        return new ResponseEntity<>(Message.success(this.adminUserService.list()), HttpStatus.OK);
    }

    @PostMapping("/loginByTel")
    @ApiOperation("手机验证登陆")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "tel", value = "手机号", required = true),
        @ApiImplicitParam(name = "code", value = "验证码", required = true)
    })
    @ApiResponses({
        @ApiResponse(code = 200, message = "接口调用成功"),
        @ApiResponse(code = 401, message = "登录信息异常,请检查 token 是否有效")
    })
    public ResponseEntity<Message<?>> loginBySms(String tel, String code) {
        // TODO: 手机验证码登录
        return null;
    }

    @PostMapping("/loginByPass")
    @ApiOperation("管理登录-密码登陆")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "userId", value = "用户名/手机号:测试账号(201731061426/17361040630)", required = true),
        @ApiImplicitParam(name = "password", value = "密码:测试密码(201731061426)", required = true)
    })
    @ApiResponses({
        @ApiResponse(code = 200, message = "接口调用成功"),
        @ApiResponse(code = 401, message = "登录信息异常,请检查 token 是否有效")
    })
    public ResponseEntity<Message<?>> loginByPassword(String username, String password) {
        log.info("--->接收到来自Web端的登录请求:[userId: {}, password: {}]", username, password);
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            throw new ApiAuthException(WebResultCode.LOGIN_FAIL, "用户名或密码不能为空");
        }

        Map<String, String> loginMap = this.adminUserService.loginByPass(username, password);
        String token = loginMap.get("token");
        boolean canLogin = Boolean.parseBoolean(loginMap.get("canLogin"));
        var data = MapUtil.newHashMap();
        data.put("token", token);
        data.put("canLogin", canLogin);
        log.info("--->>>返回认证信息:[{}]", data.toString());
        if (!canLogin) {
            // todo: 用户不存在,提示用户注册
            throw new ApiAuthException(WebResultCode.NO_USER);
        }
        data.put("userInfo", this.adminUserService.getByUserId(username));
        return new ResponseEntity<>(Message.success(data), HttpStatus.OK);
    }
}
