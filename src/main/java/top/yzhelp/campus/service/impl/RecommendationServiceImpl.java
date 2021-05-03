package top.yzhelp.campus.service.impl;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.yzhelp.campus.mapper.RecommendationMapper;
import top.yzhelp.campus.model.nt.Recommendation;
import top.yzhelp.campus.model.other.Content;
import top.yzhelp.campus.service.ContentService;
import top.yzhelp.campus.service.RecommendationService;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * @author <a href="https://github.com/gongsir0630">码之泪殇</a>
 * @date 2021/4/8 09:30
 * 你的指尖,拥有改变世界的力量
 * @description 内推信息接口实现
 */
@Service
public class RecommendationServiceImpl extends ServiceImpl<RecommendationMapper, Recommendation> implements RecommendationService {
  @Resource
  private ContentService contentService;

  @Override
  public Recommendation saveOrUpdateInfo(Recommendation info) {
    this.saveOrUpdate(info);
    Content myContent = this.contentService.getMyContent(info.getOpenId());
    ArrayList<String> publish = !StrUtil.isBlank(myContent.getPublishRecommendations())
      ? ListUtil.toList(myContent.getPublishRecommendations().split(","))
      : new ArrayList<>();
    if (!publish.contains(Integer.toString(info.getId()))) {
      publish.add(Integer.toString(info.getId()));
    }
    myContent.setPublishRecommendations(String.join(",",publish));
    this.contentService.saveOrUpdate(myContent);
    return this.getById(info.getId());
  }
}
