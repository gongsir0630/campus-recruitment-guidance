package top.yzhelp.campus.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.yzhelp.campus.model.other.Content;

/**
 * @author <a href="https://github.com/gongsir0630">码之泪殇</a>
 * @date 2021/4/8 10:04
 * 你的指尖,拥有改变世界的力量
 * @description 内容管理(发布,点赞,收藏)接口
 */
public interface ContentService extends IService<Content> {
  /**
   * 获取用户自己的内容动态
   * @param openId 用户 id
   * @return 内容动态
   */
  Content getMyContent(String openId);

  /**
   * 删除某条动态点赞
   * @param openid 用户
   * @param id 动态 id
   */
  void deleteIdFromLikes(String openid, int id);


  /**
   * 移除收藏
   * @param openid 用户
   * @param id 动态 id
   */
  void deleteIdFromCollections(String openid, int id);
}
