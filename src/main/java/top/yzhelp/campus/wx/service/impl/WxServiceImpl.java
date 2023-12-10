package top.yzhelp.campus.wx.service.impl;

import java.util.Collections;
import java.util.Objects;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.binarywang.wx.miniapp.bean.WxMaSubscribeMessage;
import lombok.extern.slf4j.Slf4j;
import top.yzhelp.campus.enums.WebResultCode;
import top.yzhelp.campus.exception.ApiAuthException;
import top.yzhelp.campus.shiro.vo.ShiroAccount;
import top.yzhelp.campus.util.JwtUtil;
import top.yzhelp.campus.wx.service.WxService;

/**
 * @author <a href="https://github.com/gongsir0630">Kyle</a>
 * @date 2021/3/29 12:59
 * @description 调用 wx-java-miniapp 实现具体微信请求
 */
@Slf4j
@Lazy
@Service
public class WxServiceImpl implements WxService {
    private static final String LOGIN_PATH = "%s/wx/user/%s/login";
    private static final String SEND_MSG_PATH = "%s/msg/%s/send";

    @Value("${wx.appid}")
    private String appid;
    @Value("${wx.url}")
    private String url;

    @Resource
    private RestTemplate restTemplate;

    /**
     * 微信小程序用户登陆，完整流程可参考下面官方地址，本例中是按此流程开发:
     * <a href="https://developers.weixin.qq.com/miniprogram/dev/framework/open-ability/login.html">小程序登录</a>
     * <li>1. 微信小程序端传入code</li>
     * <li>2. 通过wx-java-miniapp项目调用微信code2session接口获取openid和session_key</li>
     *
     * @param code 小程序端 调用 wx.login 获取到的code,用于调用 微信code2session接口
     * @return JSONObject: 包含openId和sessionKey
     */
    @Override
    public ShiroAccount login(String code) {
        // 流程: 微信登录: code + appid -> openId + session_key
        MultiValueMap<String, Object> request = new LinkedMultiValueMap<>();
        // 参数封装, 微信登录需要以下参数
        request.add("code", code);
        String path = String.format(LOGIN_PATH, url, appid);
        JSONObject dto = restTemplate.postForObject(path, request, JSONObject.class);
        log.info("调用小程序登陆接口, path=>{}, request=>{}, response=>{}", path, JSON.toJSONString(request), JSON.toJSONString(dto));
        if (Objects.isNull(dto)) {
            throw new ApiAuthException(WebResultCode.LOGIN_FAIL);
        }
        Integer errCode = dto.getInteger("code");
        if (Objects.nonNull(errCode) && errCode != 0) {
            String message = String.format("%s@%s", dto.getString("code"), dto.getString("msg"));
            throw new ApiAuthException(WebResultCode.LOGIN_FAIL, message);
        }
        // code > session success
        JSONObject data = dto.getJSONObject("data");
        // 返回 shiro 验证体
        return new ShiroAccount(data.getString("openId"), data.getString("sessionKey"), Collections.singletonList(JwtUtil.ROLE_WX));
    }

    @Override
    public void sendSubMsg(WxMaSubscribeMessage message) {
        MultiValueMap<String, String> request = new LinkedMultiValueMap<>();
        // 参数封装, 微信登录需要以下参数
        request.add("toUser", message.getToUser());
        request.add("templateId", message.getTemplateId());
        request.add("data", JSON.toJSONString(message.getData()));
        String path = String.format(SEND_MSG_PATH, url, appid);
        String res = restTemplate.postForObject(path, request, String.class);
        log.info("sendSubMsg, message=>{}, res==>{}", JSON.toJSONString(message), res);
    }
}
