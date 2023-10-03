package top.yzhelp.campus.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.binarywang.wx.miniapp.bean.WxMaSubscribeMessage;
import cn.hutool.core.date.DateUtil;
import top.yzhelp.campus.service.MsgService;
import top.yzhelp.campus.wx.service.WxService;

@Service
public class MsgServiceImpl implements MsgService {

    @Resource
    private WxService wxService;

    @Override
    public void sendMsg(String val1, String val2, String toUser, String tid) {
        WxMaSubscribeMessage message = new WxMaSubscribeMessage();
        // 消息内容
        List<WxMaSubscribeMessage.Data> data = new ArrayList<>();
        // 认证类型
        data.add(new WxMaSubscribeMessage.Data("thing1", val1));
        // 认证结果
        data.add(new WxMaSubscribeMessage.Data("phrase2", val2));
        // 认证时间
        data.add(new WxMaSubscribeMessage.Data("date3", DateUtil.today()));
        // 接收人
        message.setToUser(toUser);
        // 模板ID
        message.setTemplateId(tid);
        message.setData(data);
        wxService.sendSubMsg(message);
    }
}
