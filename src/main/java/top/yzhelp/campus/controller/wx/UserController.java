package top.yzhelp.campus.controller.wx;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import top.yzhelp.campus.controller.res.CodeMsg;
import top.yzhelp.campus.controller.res.Result;
import top.yzhelp.campus.model.yh.EduInfo;
import top.yzhelp.campus.model.yh.JobInfo;
import top.yzhelp.campus.model.yh.WxUser;
import top.yzhelp.campus.service.EduInfoService;
import top.yzhelp.campus.service.JobInfoService;
import top.yzhelp.campus.service.WxUserService;
import top.yzhelp.campus.shiro.ShiroRealm;

import javax.annotation.Resource;
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
  @Resource
  private EduInfoService eduInfoService;
  @Resource
  private JobInfoService jobInfoService;

  /**
   * 从认证信息中获取用户 openId
   * @return openId
   */
  private String getOpenId() {
    return ShiroRealm.getShiroAccount().getAuthName();
  }

  /**
   *
   * @param wxUser 小程序用户基本信息
   * @param eduInfo 教育信息
   * @param jobInfo 工作信息
   * @return 注册信息
   */
  @PostMapping("/registry")
  @ApiOperation("小程序用户注册或者用户信息更新接口")
  @ApiResponses({
    @ApiResponse(code = 200,message = "接口调用成功"),
    @ApiResponse(code = 401,message = "登录信息异常,请检查 token 是否有效")
  })
  @RequiresRoles("wx")
  public ResponseEntity<Result<Map<String,Object>>> registry(WxUser wxUser,
                                                             EduInfo eduInfo,
                                                             JobInfo jobInfo) {
    WxUser newUser = this.userService.saveOrUpdateUser(wxUser, eduInfo, jobInfo);
    Map<String,Object> data = MapUtil.newHashMap();
    data.put("userInfo",newUser);
    data.put("eduInfo",eduInfoService.getEduInfoById(newUser.getEduId()));
    data.put("jobInfo",jobInfoService.getJobInfoById(newUser.getJobId()));
    return new ResponseEntity<>(Result.success(data),HttpStatus.OK);
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
    Map<String,Object> data = MapUtil.newHashMap();
    data.put("token",token);
    data.put("canLogin",canLogin);
    log.info("--->>>返回认证信息:[{}]", data.toString());
    if (!canLogin) {
      // todo: 用户不存在,提示用户注册
      return new ResponseEntity<>(Result.fail(CodeMsg.NO_USER,data),HttpStatus.OK);
    }
    return new ResponseEntity<>(Result.success(data),HttpStatus.OK);
  }
}
