package top.yzhelp.campus.wx.service;

import top.yzhelp.campus.shiro.vo.ShiroAccount;

/**
 * @author <a href="https://github.com/gongsir0630">码之泪殇</a>
 * @date 2021/3/29 12:58
 * 你的指尖,拥有改变世界的力量
 * @description 对接微信服务端
 */
public interface WxService {
  /**
   * 微信小程序用户登陆，完整流程可参考下面官方地址，本例中是按此流程开发
   * https://developers.weixin.qq.com/miniprogram/dev/framework/open-ability/login.html
   * 1 . 微信小程序端传入code。
   * 2 . 通过wx-java-miniapp项目调用微信code2session接口获取openid和session_key
   *
   * @param code 小程序端 调用 wx.login 获取到的code,用于调用 微信code2session接口
   * @return JSONObject: 包含openId和sessionKey
   */
  ShiroAccount login(String code);
}
