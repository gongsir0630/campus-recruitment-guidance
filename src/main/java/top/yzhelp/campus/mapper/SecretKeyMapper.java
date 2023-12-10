package top.yzhelp.campus.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import top.yzhelp.campus.model.SecretKey;

/**
 * @author <a href="https://github.com/gongsir0630">Kyle</a>
 * @date 2021/4/10 19:37
 * @description 秘钥信息 mapper
 */
@Mapper
public interface SecretKeyMapper extends BaseMapper<SecretKey> {
}
