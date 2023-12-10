package top.yzhelp.campus.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import top.yzhelp.campus.model.user.Member;

/**
 * @author <a href="https://github.com/gongsir0630">Kyle</a>
 * @date 2021/4/8 09:35
 * @description 柚子帮成员 mapper
 */
@Mapper
public interface MemberMapper extends BaseMapper<Member> {
}
