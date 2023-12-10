package top.yzhelp.campus.controller.wx;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import top.yzhelp.campus.enums.WebResultCode;
import top.yzhelp.campus.exception.ApiAuthException;
import top.yzhelp.campus.model.base.EduInfo;
import top.yzhelp.campus.model.base.JobInfo;
import top.yzhelp.campus.model.common.Message;
import top.yzhelp.campus.model.user.CrgWxUser;
import top.yzhelp.campus.service.WxUserService;
import top.yzhelp.campus.shiro.ShiroRealm;
import top.yzhelp.campus.util.JwtUtil;

/**
 * @author <a href="https://github.com/gongsir0630">Kyle</a>
 * @date 2021/3/29 21:04
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
     *
     * @return openId
     */
    private String getOpenId() {
        return ShiroRealm.getShiroAccount().getAuthName();
    }

    @GetMapping("/{openId}")
    @ApiOperation("获取用户信息")
    public ResponseEntity<Message<?>> getUserInfoByOpenId(@PathVariable String openId) {
        CrgWxUser userInfo = this.userService.getUserInfo(openId);
        return new ResponseEntity<>(Message.success(userInfo), HttpStatus.OK);
    }

    @PostMapping("/names")
    @ApiOperation("id转昵称")
    public ResponseEntity<Message<?>> ids2Names(String ids) {
        ArrayList<String> openIds = ListUtil.toList(ids.split(","));
        openIds.removeIf(StrUtil::isBlank);
        List<String> names = new ArrayList<>();
        openIds.forEach(openId -> names.add(this.userService.getUserInfo(openId).getNickName()));
        return new ResponseEntity<>(Message.success(names), HttpStatus.OK);
    }

    @GetMapping("/mine")
    @ApiOperation("已经授权情况下获取自己的个人信息")
    @RequiresRoles("wx")
    public ResponseEntity<Message<?>> getProfile() {
        CrgWxUser newUser = this.userService.getUserInfo(getOpenId());
        Map<String, Object> data = MapUtil.newHashMap();
        data.put("userInfo", newUser);
        return new ResponseEntity<>(Message.success(data), HttpStatus.OK);
    }

    /**
     * 用户信息注册或更新
     *
     * @param json 用户信息
     */
    @PostMapping("/registry")
    @ApiOperation("小程序用户注册或者用户信息更新接口")
    @RequiresRoles("wx")
    public ResponseEntity<Message<Map<String, Object>>> registry(@RequestBody String json) {
        log.info("--->>>json信息：[{}]", json);
        JSONObject jsonObject = JSON.parseObject(json);
        CrgWxUser wxUser = jsonObject.getObject("wxUser", CrgWxUser.class);
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
        CrgWxUser newUser = this.userService.saveOrUpdateUser(wxUser, eduInfo, jobInfo);
        Map<String, Object> data = MapUtil.newHashMap();
        data.put("userInfo", newUser);
        return new ResponseEntity<>(Message.success(data), HttpStatus.OK);
    }

    /**
     * 小程序用户登录接口: 通过js_code换取openId, 判断用户是否已经注册
     *
     * @param code wx.login() 得到的code凭证
     * @return token
     */
    @PostMapping("/login")
    @ApiOperation("用户登录")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "code", value = "小程序的 js_code", required = true)
    })
    @ApiResponses({
        @ApiResponse(code = 200, message = "接口调用成功"),
        @ApiResponse(code = 401, message = "登录信息异常,请检查 token 是否有效")
    })
    public ResponseEntity<Message<Map<String, Object>>> login(String code) {
        if (StringUtils.isBlank(code)) {
            throw new ApiAuthException(WebResultCode.LOGIN_FAIL, "code is empty");
        }
        log.info("微信小程序用户登陆, code===>{}", code);
        String token = userService.login(code);
        if (StringUtils.isBlank(token)) {
            // ok: 用户不存在,提示用户注册
            throw new ApiAuthException(WebResultCode.NO_USER);
        }
        Map<String, Object> data = MapUtil.newHashMap();
        data.put("token", token);
        data.put("canLogin", true);
        data.put("userInfo", this.userService.getUserInfo(jwtUtil.getAuthName(token)));
        return new ResponseEntity<>(Message.success(data), HttpStatus.OK);
    }
}
