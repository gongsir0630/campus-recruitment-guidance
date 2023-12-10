package top.yzhelp.campus.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import top.yzhelp.campus.model.CrgAppInfo;

/**
 * @author <a href="https://github.com/gongsir0630">Kyle</a>
 * @date 2021/4/8 09:32
 * @description 小程序信息
 */
@Mapper
public interface AppInfoMapper extends BaseMapper<CrgAppInfo> {
}
