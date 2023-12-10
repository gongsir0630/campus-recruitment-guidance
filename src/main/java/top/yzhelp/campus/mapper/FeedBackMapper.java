package top.yzhelp.campus.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import top.yzhelp.campus.model.FeedBack;

/**
 * @author <a href="https://github.com/gongsir0630">Kyle</a>
 * @date 2021/4/8 15:43
 * @description 问题反馈 Mapper
 */
@Mapper
public interface FeedBackMapper extends BaseMapper<FeedBack> {
}
