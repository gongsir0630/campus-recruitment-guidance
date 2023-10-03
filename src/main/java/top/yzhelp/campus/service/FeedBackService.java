package top.yzhelp.campus.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

import top.yzhelp.campus.model.FeedBack;

/**
 * @author <a href="https://github.com/gongsir0630">Kyle</a>
 * @date 2021/4/8 15:45
 * 你的指尖,拥有改变世界的力量
 * @description 小程序问题反馈接口
 */
public interface FeedBackService extends IService<FeedBack> {
    /**
     * 获取指定用户的反馈
     *
     * @param openId 用户id
     * @return list
     */
    List<FeedBack> getFeedBackByOpenId(String openId);

    /**
     * 所有数据
     *
     * @return list
     */
    List<FeedBack> getAll();
}
