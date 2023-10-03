package top.yzhelp.campus.wx.service;

import cn.binarywang.wx.miniapp.bean.WxMaSubscribeMessage;
import top.yzhelp.campus.shiro.vo.ShiroAccount;

/**
 * @author <a href="https://github.com/gongsir0630">Kyle</a>
 * @date 2021/3/29 12:58
 * 你的指尖,拥有改变世界的力量
 * @description 对接微信服务端
 */
public interface WxService {

    /**
     * 小程序登陆
     *
     * @param code wx生成的临时code
     * @return 用户登陆信息
     */
    ShiroAccount login(String code);

    /**
     * 发送订阅消息
     *
     * @param message 订阅消息内容
     */
    void sendSubMsg(WxMaSubscribeMessage message);
}
