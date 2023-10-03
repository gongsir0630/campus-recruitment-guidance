package top.yzhelp.campus.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import top.yzhelp.campus.model.user.CrgWxUser;

/**
 * @author <a href="https://github.com/gongsir0630">Kyle</a>
 * @date 2021/3/29 12:47
 * 你的指尖,拥有改变世界的力量
 * @description 动态 mapper
 */
@Mapper
public interface WxUserMapper extends BaseMapper<CrgWxUser> {
}
