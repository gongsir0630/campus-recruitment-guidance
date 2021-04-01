package top.yzhelp.campus.wx.service.impl;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import top.yzhelp.campus.controller.res.CodeMsg;
import top.yzhelp.campus.exception.ApiAuthException;
import top.yzhelp.campus.shiro.vo.ShiroAccount;
import top.yzhelp.campus.util.JwtUtil;
import top.yzhelp.campus.wx.service.WxService;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;

/**
 * @author <a href="https://github.com/gongsir0630">码之泪殇</a>
 * @date 2021/3/29 12:59
 * 你的指尖,拥有改变世界的力量
 * @description 调用 wx-java-miniapp 实现具体微信请求
 */
@Service
@Slf4j
public class WxServiceImpl implements WxService {

  @Value("${wx.appid}")
  private String appid;
  @Value("${wx.url}")
  private String url;

  @Resource
  private RestTemplate restTemplate;

  @Override
  public ShiroAccount login(String code) {
    // 流程: 微信登录: code + appid -> openId + session_key
    // appid: 从配置文件读取
    MultiValueMap<String, Object> request = new LinkedMultiValueMap<>();
    // 参数封装, 微信登录需要以下参数
    request.add("code", code);
    // eg: http://localhost:8081/wx/user/{appid}/login
    String path = url+"/user/"+appid+"/login";
    // 请求
    JSONObject dto = restTemplate.postForObject(path, request, JSONObject.class);
    log.info("--->>>来自[{}]的返回 = [{}]",path,dto);
    int errCode = -1;
    if (dto != null ) {
      errCode = Integer.parseInt(dto.get("code").toString());
    } else {
      throw new ApiAuthException(CodeMsg.LOGIN_FAIL);
    }
    if (0 != errCode) {
      throw new ApiAuthException(new CodeMsg(Integer.parseInt(dto.get("code").toString()),
        dto.get("msg").toString()));
    }
    // code2session success
    JSONObject data = dto.getJSONObject("data");
    // 返回 shiro 验证体
    return new ShiroAccount(data.getString("openId")
      ,data.getString("sessionKey"), Collections.singletonList(JwtUtil.ROLE_WX));
  }
}
