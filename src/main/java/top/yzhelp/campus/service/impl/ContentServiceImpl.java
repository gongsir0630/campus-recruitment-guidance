package top.yzhelp.campus.service.impl;

import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.yzhelp.campus.mapper.ContentMapper;
import top.yzhelp.campus.model.other.Content;
import top.yzhelp.campus.service.ContentService;

import java.util.ArrayList;

/**
 * @author <a href="https://github.com/gongsir0630">码之泪殇</a>
 * @date 2021/4/8 10:05
 * 你的指尖,拥有改变世界的力量
 * @description description
 */
@Service
public class ContentServiceImpl extends ServiceImpl<ContentMapper, Content> implements ContentService {
  /**
   * 获取用户自己的内容动态
   *
   * @param openId 用户 id
   * @return 内容动态
   */
  @Override
  public Content getMyContent(String openId) {
    return this.getById(openId);
  }

  /**
   * 删除某条动态点赞
   *
   * @param openid 用户
   * @param id     动态 id
   */
  @Override
  public void deleteIdFromLikes(String openid, int id) {
    Content myContent = this.getMyContent(openid);
    ArrayList<String> myLikes = ListUtil.toList(myContent.getLikeNews().split(","));
    myLikes.remove(Integer.toString(id));
    myContent.setLikeNews(String.join(",",myLikes));
    this.updateById(myContent);
  }

  /**
   * 移除收藏
   *
   * @param openid 用户
   * @param id     动态 id
   */
  @Override
  public void deleteIdFromCollections(String openid, int id) {
    Content myContent = this.getMyContent(openid);
    ArrayList<String> myCollections = ListUtil.toList(myContent.getCollectNews().split(","));
    myCollections.remove(Integer.toString(id));
    myContent.setCollectNews(String.join(",",myCollections));
    this.updateById(myContent);
  }
}
