package top.yzhelp.campus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.yzhelp.campus.model.yzb.Member;

/**
 * @author <a href="https://github.com/gongsir0630">码之泪殇</a>
 * @date 2021/4/8 09:35
 * 你的指尖,拥有改变世界的力量
 * @description 柚子帮成员 mapper
 */
@Mapper
public interface MemberMapper extends BaseMapper<Member> {
}
