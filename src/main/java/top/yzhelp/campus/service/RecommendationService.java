package top.yzhelp.campus.service;

import com.baomidou.mybatisplus.extension.service.IService;

import top.yzhelp.campus.model.Recommendation;

/**
 * @author <a href="https://github.com/gongsir0630">Kyle</a>
 * @date 2021/4/8 09:29
 * @description 内推信息接口
 */
public interface RecommendationService extends IService<Recommendation> {
    /**
     * 发布、更新
     *
     * @param info 内推信息
     * @return 内推信息
     */
    Recommendation saveOrUpdateInfo(Recommendation info);
}
