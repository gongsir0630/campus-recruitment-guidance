package top.yzhelp.campus.controller.wx.me;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
import top.yzhelp.campus.service.WxUserService;
import top.yzhelp.campus.shiro.ShiroRealm;
import top.yzhelp.campus.util.JwtUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
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
@Api(tags = "MINIAPP-小程序用户接口")
public class UserController {

  @Resource
  private WxUserService userService;
  @Resource
  private JwtUtil jwtUtil;

  /**
   * 从认证信息中获取用户 openId
   * @return openId
   */
  private String getOpenId() {
    return ShiroRealm.getShiroAccount().getAuthName();
  }

  @GetMapping("/{openId}")
  @ApiOperation("获取用户信息")
  public ResponseEntity<Result<?>> getUserInfoByOpenId(@PathVariable String openId) {
    WxUser userInfo = this.userService.getUserInfo(openId);
    return new ResponseEntity<>(Result.success(userInfo),HttpStatus.OK);
  }

  @PostMapping("/names")
  @ApiOperation("id转昵称")
  public ResponseEntity<Result<?>> ids2Names(String ids) {
    ArrayList<String> openIds = ListUtil.toList(ids.split(","));
    openIds.removeIf(StrUtil::isBlank);
    List<String> names = new ArrayList<>();
    openIds.forEach(openId -> names.add(this.userService.getUserInfo(openId).getNickName()));
    return new ResponseEntity<>(Result.success(names),HttpStatus.OK);
  }

  @GetMapping("/mine")
  @ApiOperation("已经授权情况下获取自己的个人信息")
  @RequiresRoles("wx")
  public ResponseEntity<Result<?>> getProfile() {
    WxUser newUser = this.userService.getUserInfo(getOpenId());
    Map<String,Object> data = MapUtil.newHashMap();
    data.put("userInfo",newUser);
    return new ResponseEntity<>(Result.success(data),HttpStatus.OK);
  }

  /**
   * 用户信息注册或更新
   * @param json 用户信息
   */
  @PostMapping("/registry")
  @ApiOperation("小程序用户注册或者用户信息更新接口")
  @RequiresRoles("wx")
  public ResponseEntity<Result<Map<String,Object>>> registry(@RequestBody String json) {
    log.info("--->>>json信息：[{}]",json);
    JSONObject jsonObject = JSON.parseObject(json);
    WxUser wxUser = jsonObject.getObject("wxUser", WxUser.class);
    wxUser.setOpenId(getOpenId());
    EduInfo eduInfo = jsonObject.getObject("eduInfo", EduInfo.class);
    if (eduInfo.getId() == 0) {
      eduInfo.setId(null);
    }
    eduInfo.setOpenId(getOpenId());
    JobInfo jobInfo = jsonObject.getObject("jobInfo", JobInfo.class);
    if (jobInfo.getId() == -1) {
      // 用户未提交工作信息，不注册
      jobInfo = null;
    } else {
      if (jobInfo.getId() == 0) {
        // 首次提交信息，id设置为null，数据库自增
        jobInfo.setId(null);
      }
      jobInfo.setOpenId(getOpenId());
    }
    WxUser newUser = this.userService.saveOrUpdateUser(wxUser, eduInfo, jobInfo);
    Map<String,Object> data = MapUtil.newHashMap();
    data.put("userInfo",newUser);
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
      // ok: 用户不存在,提示用户注册
      return new ResponseEntity<>(Result.fail(CodeMsg.NO_USER,data),HttpStatus.OK);
    }
    data.put("userInfo",this.userService.getUserInfo(jwtUtil.getAuthName(token)));
    return new ResponseEntity<>(Result.success(data),HttpStatus.OK);
  }
}
