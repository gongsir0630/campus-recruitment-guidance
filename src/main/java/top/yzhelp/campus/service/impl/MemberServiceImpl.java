package top.yzhelp.campus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.yzhelp.campus.mapper.MemberMapper;
import top.yzhelp.campus.model.yzb.Member;
import top.yzhelp.campus.service.MemberService;

/**
 * @author <a href="https://github.com/gongsir0630">码之泪殇</a>
 * @date 2021/4/8 09:36
 * 你的指尖,拥有改变世界的力量
 * @description 柚子帮成员接口实现
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {
}
