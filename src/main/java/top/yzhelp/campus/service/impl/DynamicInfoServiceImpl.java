package top.yzhelp.campus.service.impl;

import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.yzhelp.campus.mapper.DynamicInfoMapper;
import top.yzhelp.campus.model.dt.DynamicInfo;
import top.yzhelp.campus.model.other.Content;
import top.yzhelp.campus.service.ContentService;
import top.yzhelp.campus.service.DynamicInfoService;
import top.yzhelp.campus.service.WxUserService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="https://github.com/gongsir0630">码之泪殇</a>
 * @date 2021/4/8 08:58
 * 你的指尖,拥有改变世界的力量
 * @description 小程序动态功能模块 -> 接口实现
 */
@Service
public class DynamicInfoServiceImpl extends ServiceImpl<DynamicInfoMapper, DynamicInfo> implements DynamicInfoService {
  @Resource
  private WxUserService wxUserService;

  @Resource
  private ContentService contentService;

  /**
   * 获取所有动态列表
   *
   * @return list
   */
  @Override
  public List<DynamicInfo> getAllDtInfoList() {
    return this.list(new QueryWrapper<DynamicInfo>().orderByDesc("id"));
  }

  /**
   * 点赞动态
   *
   * @param id 动态 id
   * @param openId 用户
   */
  @Override
  public void likeById(int id,String openId) {
    // 更新当前动态的点赞列表
    DynamicInfo dt = this.getDtDetailById(id);
    ArrayList<String> likes = ListUtil.toList(dt.getLikeList().split(","));
    // 更新用户自己的点赞列表
    Content myContent = this.contentService.getMyContent(openId);
    ArrayList<String> myLikes = ListUtil.toList(myContent.getLikeNews().split(","));
    if (!likes.contains(openId)) {
      // 点赞
      likes.add(openId);
      myLikes.add(Integer.toString(id));
    } else {
      // 取消点赞
      likes.remove(openId);
      myLikes.remove(Integer.toString(id));
    }
    dt.setLikeList(String.join(",",likes));
    this.updateById(dt);
    myContent.setLikeNews(String.join(",",myLikes));
    this.contentService.updateById(myContent);
  }

  /**
   * 动态详情
   *
   * @param id 动态 id
   * @return 动态详情
   */
  @Override
  public DynamicInfo getDtDetailById(int id) {
    return this.getById(id);
  }

  /**
   * 收藏
   *
   * @param id     动态 id
   * @param openId 用户
   */
  @Override
  public void collection(int id, String openId) {
    // 更新用户自己的收藏列表
    Content myContent = this.contentService.getMyContent(openId);
    ArrayList<String> myCollections = ListUtil.toList(myContent.getCollectNews().split(","));
    if (!myCollections.contains(Integer.toString(id))) {
      myCollections.add(Integer.toString(id));
    } else {
      // 取消收藏
      myCollections.remove(Integer.toString(id));
    }
    myContent.setCollectNews(String.join(",",myCollections));
    this.contentService.updateById(myContent);
  }

  /**
   * 删除动态
   *
   * @param id 动态 id
   */
  @Override
  public void deleteDtById(int id) {
    DynamicInfo dt = this.getDtDetailById(id);
    // todo: 删除所有点赞用户的点赞记录
    ArrayList<String> likes = ListUtil.toList(dt.getLikeList().split(","));
    likes.forEach(openid -> {
      this.contentService.deleteIdFromLikes(openid,id);
    });
    // todo: 删除所有收藏用户的收藏记录
    ArrayList<String> collections = ListUtil.toList(dt.getCollectionList().split(","));
    collections.forEach(openid -> {
      this.contentService.deleteIdFromCollections(openid,id);
    });
    // 删除动态
    this.removeById(id);
  }

  /**
   * 发布 | 更新
   *
   * @param info 动态信息
   * @return 动态详情
   */
  @Override
  public DynamicInfo saveOrUpdateDt(DynamicInfo info) {
    this.saveOrUpdate(info);
    return this.getDtDetailById(info.getId());
  }
}
