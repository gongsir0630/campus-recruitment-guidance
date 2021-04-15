package top.yzhelp.campus.service.impl;

import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.yzhelp.campus.mapper.MemberMapper;
import top.yzhelp.campus.model.other.Content;
import top.yzhelp.campus.model.yzb.Member;
import top.yzhelp.campus.service.ContentService;
import top.yzhelp.campus.service.MemberService;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * @author <a href="https://github.com/gongsir0630">码之泪殇</a>
 * @date 2021/4/8 09:36
 * 你的指尖,拥有改变世界的力量
 * @description 柚子帮成员接口实现
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

  @Resource
  private ContentService contentService;

  /**
   * 获取柚子帮成员详情
   *
   * @param openId 用户 id
   * @return 成员详情
   */
  @Override
  public Member getMemberDetailByOpenId(String openId) {
    return this.getOne(new LambdaQueryWrapper<Member>().eq(Member::getOpenId,openId));
  }

  /**
   * 获取柚子帮成员详情
   *
   * @param id 成员 id
   * @return 成员详情
   */
  @Override
  public Member getMemberDetailById(int id) {
    return this.getById(id);
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
    return this.getById(member.getId());
  }

  /**
   * 柚子帮成员列表
   *
   * @param cur  当前页
   * @param size 每页数量
   * @return 分页
   */
  @Override
  public IPage<Member> getAllMemberList(int cur, int size) {
    return this.page(new Page<>(cur,size)
      ,new LambdaQueryWrapper<Member>().orderByDesc(Member::getLikeCount).orderByDesc(Member::getId));
  }

  /**
   * like
   *
   * @param id     成员 id
   * @param openId 当前用户
   */
  @Override
  public void likeById(int id, String openId) {
    // 更新当前成员的点赞列表
    Member detail = this.getMemberDetailById(id);
    ArrayList<String> likes = ListUtil.toList(detail.getLikeList().split(","));
    // 更新用户自己的点赞列表
    Content myContent = this.contentService.getMyContent(openId);
    ArrayList<String> myLikes = ListUtil.toList(myContent.getLikeNews().split(","));
    if (!likes.contains(openId)) {
      // 点赞
      likes.add(openId);
      myLikes.add(Integer.toString(id));
      detail.setLikeCount(detail.getLikeCount()+1);
    } else {
      // 取消点赞
      likes.remove(openId);
      myLikes.remove(Integer.toString(id));
      detail.setLikeCount(detail.getLikeCount()-1);
    }
  }
}
