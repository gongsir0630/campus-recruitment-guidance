package top.yzhelp.campus.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import top.yzhelp.campus.mapper.RecommendationMapper;
import top.yzhelp.campus.model.Recommendation;
import top.yzhelp.campus.service.RecommendationService;

/**
 * @author <a href="https://github.com/gongsir0630">Kyle</a>
 * @date 2021/4/8 09:30
 * 你的指尖,拥有改变世界的力量
 * @description 内推信息接口实现
 */
@Service
public class RecommendationServiceImpl extends ServiceImpl<RecommendationMapper, Recommendation>
    implements RecommendationService {

    @Override
    public Recommendation saveOrUpdateInfo(Recommendation info) {
        this.saveOrUpdate(info);
        return this.getById(info.getId());
    }
}
