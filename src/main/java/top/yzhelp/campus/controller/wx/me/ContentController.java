package top.yzhelp.campus.controller.wx.me;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yzhelp.campus.controller.res.Result;
import top.yzhelp.campus.model.dt.DynamicInfo;
import top.yzhelp.campus.model.nt.Recommendation;
import top.yzhelp.campus.model.other.Content;
import top.yzhelp.campus.model.yzb.Member;
import top.yzhelp.campus.service.DynamicInfoService;
import top.yzhelp.campus.service.MemberService;
import top.yzhelp.campus.service.RecommendationService;
import top.yzhelp.campus.shiro.ShiroRealm;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author <a href="https://github.com/gongsir0630">码之泪殇</a>
 * @date 2021/4/26 11:49
 * 你的指尖,拥有改变世界的力量
 * @description description
 */
@RestController
@Slf4j
@RequestMapping("wx/content")
@Api(tags = "MINIAPP-用户内容管理接口")
public class ContentController {

  @Resource
  private DynamicInfoService dynamicInfoService;
  @Resource
  private RecommendationService recommendationService;
  @Resource
  private MemberService memberService;

  /**
   * 当前用户的用户ID
   * @return openId
   */
  private String getOpenId() {
    return ShiroRealm.getShiroAccount().getAuthName();
  }

  @GetMapping("/me")
  @ApiOperation("获取当前用户的发布、点赞、收藏")
  @RequiresRoles("wx")
  public ResponseEntity<Result<?>> getEduInfoById() {
    Content content = new Content();
    content.setOpenId(getOpenId());
    // ---动态---
    // 我的发布
    List<DynamicInfo> publishNews = dynamicInfoService.list(
      new LambdaQueryWrapper<DynamicInfo>()
        .eq(DynamicInfo::getOpenId, getOpenId()));
    content.setPublishNews(publishNews.stream().map(DynamicInfo::getId).collect(Collectors.toList()));
    // 我的点赞
    List<DynamicInfo> likeNews = dynamicInfoService.list(
      new LambdaQueryWrapper<DynamicInfo>()
        .like(DynamicInfo::getLikeList, getOpenId()));
    content.setLikeNews(likeNews.stream().map(DynamicInfo::getId).collect(Collectors.toList()));
    // 我的收藏
    List<DynamicInfo> collectNews = dynamicInfoService.list(
      new LambdaQueryWrapper<DynamicInfo>()
        .like(DynamicInfo::getCollectionList, getOpenId()));
    content.setCollectNews(collectNews.stream().map(DynamicInfo::getId).collect(Collectors.toList()));

    // ---内推---
    // 我的发布
    List<Recommendation> publishRecommendations = recommendationService.list(
      new LambdaQueryWrapper<Recommendation>()
        .eq(Recommendation::getOpenId, getOpenId()));
    content.setPublishRecommendations(publishRecommendations.stream().map(Recommendation::getId).collect(Collectors.toList()));

    // ---柚子帮成员---
    // 关注我的
    Member member = memberService.getOne(
      new LambdaQueryWrapper<Member>()
        .eq(Member::getOpenId, getOpenId())
    );
    if (member != null) {
      content.setFollowMe(!StrUtil.isBlank(member.getLikeList())
        ? ListUtil.toList()
        : new ArrayList<>());
    }

    // 我关注的
    List<Member> myFollows = memberService.list(
      new LambdaQueryWrapper<Member>()
        .like(Member::getLikeList, getOpenId())
    );
    content.setMyFollow(myFollows.stream().map(Member::getId).collect(Collectors.toList()));
    return new ResponseEntity<>(Result.success(content), HttpStatus.OK);
  }
}
