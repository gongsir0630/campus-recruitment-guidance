package top.yzhelp.campus.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import top.yzhelp.campus.model.Recommendation;

/**
 * @author <a href="https://github.com/gongsir0630">Kyle</a>
 * @date 2021/4/8 09:28
 * 你的指尖,拥有改变世界的力量
 * @description 小程序内推信息 mapper
 */
@Mapper
public interface RecommendationMapper extends BaseMapper<Recommendation> {
}
