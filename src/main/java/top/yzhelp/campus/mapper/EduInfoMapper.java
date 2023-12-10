package top.yzhelp.campus.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import top.yzhelp.campus.model.base.EduInfo;

/**
 * @author <a href="https://github.com/gongsir0630">Kyle</a>
 * @date 2021/4/8 16:11
 * @description 教育信息 Mapper
 */
@Mapper
public interface EduInfoMapper extends BaseMapper<EduInfo> {
}
