package top.yzhelp.campus.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import top.yzhelp.campus.model.base.JobInfo;

/**
 * @author <a href="https://github.com/gongsir0630">Kyle</a>
 * @date 2021/4/8 16:15
 * @description 用户工作信息 Mapper
 */
@Mapper
public interface JobInfoMapper extends BaseMapper<JobInfo> {
}
