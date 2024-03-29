package top.yzhelp.campus.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import top.yzhelp.campus.model.base.School;

/**
 * @author <a href="https://github.com/gongsir0630">Kyle</a>
 * @date 2021/4/8 16:18
 * @description 学校信息 Mapper
 */
@Mapper
public interface SchoolMapper extends BaseMapper<School> {
}
