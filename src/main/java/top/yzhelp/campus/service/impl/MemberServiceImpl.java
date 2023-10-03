package top.yzhelp.campus.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.StrUtil;
import top.yzhelp.campus.mapper.MemberMapper;
import top.yzhelp.campus.model.user.Member;
import top.yzhelp.campus.service.MemberService;
import top.yzhelp.campus.service.WxUserService;

/**
 * @author <a href="https://github.com/gongsir0630">Kyle</a>
 * @date 2021/4/8 09:36
 * 你的指尖,拥有改变世界的力量
 * @description 柚子帮成员接口实现
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    @Resource
    private WxUserService userService;

    /**
     * 获取柚子帮成员详情
     *
     * @param openId 用户 id
     * @return 成员详情
     */
    @Override
    public Member getMemberDetailByOpenId(String openId) {
        Member member = this.getOne(new LambdaQueryWrapper<Member>().eq(Member::getOpenId, openId));
        if (member != null) {
            member.setUser(userService.getUserInfo(member.getOpenId()));
        }
        return member;
    }

    /**
     * 获取柚子帮成员详情
     *
     * @param id 成员 id
     * @return 成员详情
     */
    @Override
    public Member getMemberDetailById(int id) {
        Member member = this.getById(id);
        if (member != null) {
            member.setUser(userService.getUserInfo(member.getOpenId()));
        }
        return member;
    }

    /**
     * 添加 | 更新
     *
     * @param member 成员详情
     * @return 成员详情
     */
    @Override
    public Member saveOrUpdateMember(Member member) {
        this.saveOrUpdate(member);
        return this.getMemberDetailById(member.getId());
    }

    /**
     * 柚子帮成员列表
     *
     * @return list
     */
    @Override
    public List<Member> getAllMemberList() {
        List<Member> list = this.list(new LambdaQueryWrapper<Member>().orderByDesc(Member::getLikeCount));
        list.forEach(member -> member.setUser(this.userService.getUserInfo(member.getOpenId())));
        return list;
    }

    /**
     * like
     *
     * @param id 成员 id
     * @param openId 当前用户
     */
    @Override
    public void likeById(int id, String openId) {
        // 更新当前成员的点赞列表
        Member detail = this.getMemberDetailById(id);
        ArrayList<String> likes = !StrUtil.isBlank(detail.getLikeList())
                                  ? ListUtil.toList(detail.getLikeList().split(","))
                                  : new ArrayList<>();
        if (!likes.contains(openId)) {
            // 点赞
            likes.add(openId);
            detail.setLikeCount(detail.getLikeCount() + 1);
        } else {
            // 取消点赞
            likes.remove(openId);
            detail.setLikeCount(detail.getLikeCount() > 0 ? detail.getLikeCount() - 1 : 0);
        }
        detail.setLikeList(String.join(",", likes));
        this.updateById(detail);
    }
}
