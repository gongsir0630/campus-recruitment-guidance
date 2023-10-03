package top.yzhelp.campus.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import top.yzhelp.campus.mapper.FeedBackMapper;
import top.yzhelp.campus.model.FeedBack;
import top.yzhelp.campus.service.FeedBackService;

/**
 * @author <a href="https://github.com/gongsir0630">Kyle</a>
 * @date 2021/4/8 15:46
 * 你的指尖,拥有改变世界的力量
 * @description 反馈信息接口实现
 */
@Service
public class FeedBackServiceImpl extends ServiceImpl<FeedBackMapper, FeedBack> implements FeedBackService {
    /**
     * 获取指定用户的反馈
     *
     * @param openId 用户id
     * @return list
     */
    @Override
    public List<FeedBack> getFeedBackByOpenId(String openId) {
        return this.list(new LambdaQueryWrapper<FeedBack>()
            .eq(FeedBack::getOpenId, openId)
            .orderByDesc(FeedBack::getId));
    }

    @Override
    public List<FeedBack> getAll() {
        return this.list(new LambdaQueryWrapper<FeedBack>().orderByDesc(FeedBack::getId));
    }
}
