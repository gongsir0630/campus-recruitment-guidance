package top.yzhelp.campus.service;

public interface MsgService {
    /**
     * 发送订阅消息
     *
     * @param val1 认证类型
     * @param val2 认证结果
     * @param toUser 接受者
     * @param tid 模板ID
     */
    void sendMsg(String val1, String val2, String toUser, String tid);
}
