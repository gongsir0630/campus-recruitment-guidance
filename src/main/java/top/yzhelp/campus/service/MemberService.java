package top.yzhelp.campus.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

import top.yzhelp.campus.model.user.Member;

/**
 * @author <a href="https://github.com/gongsir0630">Kyle</a>
 * @date 2021/4/8 09:36
 * 你的指尖,拥有改变世界的力量
 * @description 柚子帮成员接口
 */
public interface MemberService extends IService<Member> {
    /**
     * 获取柚子帮成员详情
     *
     * @param openId 用户 id
     * @return 成员详情
     */
    Member getMemberDetailByOpenId(String openId);

    /**
     * 获取柚子帮成员详情
     *
     * @param id 成员 id
     * @return 成员详情
     */
    Member getMemberDetailById(int id);

    /**
     * 添加 | 更新
     *
     * @param member 成员详情
     * @return 成员详情
     */
    Member saveOrUpdateMember(Member member);

    /**
     * 柚子帮成员列表
     *
     * @return list
     */
    List<Member> getAllMemberList();

    /**
     * like
     *
     * @param id 成员 id
     * @param openId 当前用户
     */
    void likeById(int id, String openId);
}
