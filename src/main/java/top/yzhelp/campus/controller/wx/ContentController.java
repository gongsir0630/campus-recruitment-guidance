package top.yzhelp.campus.controller.wx;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import top.yzhelp.campus.model.CrgDynamicInfo;
import top.yzhelp.campus.model.Recommendation;
import top.yzhelp.campus.model.common.Message;
import top.yzhelp.campus.model.user.Member;
import top.yzhelp.campus.service.DynamicInfoService;
import top.yzhelp.campus.service.MemberService;
import top.yzhelp.campus.service.RecommendationService;
import top.yzhelp.campus.shiro.ShiroRealm;
import top.yzhelp.campus.vo.UserProfileContentVO;

/**
 * @author <a href="https://github.com/gongsir0630">Kyle</a>
 * @date 2021/4/26 11:49
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
     *
     * @return openId
     */
    private String getOpenId() {
        return ShiroRealm.getShiroAccount().getAuthName();
    }

    @GetMapping("/me")
    @ApiOperation("获取当前用户的发布、点赞、收藏")
    @RequiresRoles("wx")
    public ResponseEntity<Message<?>> getEduInfoById() {
        UserProfileContentVO contentVO = new UserProfileContentVO();
        contentVO.setOpenId(getOpenId());
        // ---动态---
        // 我的发布
        List<CrgDynamicInfo> publishNews = dynamicInfoService.list(
            new LambdaQueryWrapper<CrgDynamicInfo>()
                .eq(CrgDynamicInfo::getOpenId, getOpenId()));
        contentVO.setPublishNews(publishNews.stream().map(CrgDynamicInfo::getId).collect(Collectors.toList()));
        // 我的点赞
        List<CrgDynamicInfo> likeNews = dynamicInfoService.list(
            new LambdaQueryWrapper<CrgDynamicInfo>()
                .like(CrgDynamicInfo::getLikeList, getOpenId()));
        contentVO.setLikeNews(likeNews.stream().map(CrgDynamicInfo::getId).collect(Collectors.toList()));
        // 我的收藏
        List<CrgDynamicInfo> collectNews = dynamicInfoService.list(
            new LambdaQueryWrapper<CrgDynamicInfo>()
                .like(CrgDynamicInfo::getCollectionList, getOpenId()));
        contentVO.setCollectNews(collectNews.stream().map(CrgDynamicInfo::getId).collect(Collectors.toList()));

        // ---内推---
        // 我的发布
        List<Recommendation> publishRecommendations = recommendationService.list(
            new LambdaQueryWrapper<Recommendation>()
                .eq(Recommendation::getOpenId, getOpenId()));
        contentVO.setPublishRecommendations(
            publishRecommendations.stream().map(Recommendation::getId).collect(Collectors.toList()));

        // ---柚子帮成员---
        // 关注我的
        Member member = memberService.getOne(
            new LambdaQueryWrapper<Member>()
                .eq(Member::getOpenId, getOpenId())
        );
        if (member != null) {
            contentVO.setFollowMe(!StrUtil.isBlank(member.getLikeList())
                                ? ListUtil.toList(member.getLikeList().split(","))
                                : new ArrayList<>());
        }

        // 我关注的
        List<Member> myFollows = memberService.list(
            new LambdaQueryWrapper<Member>()
                .like(Member::getLikeList, getOpenId())
        );
        contentVO.setMyFollow(myFollows.stream().map(Member::getId).collect(Collectors.toList()));
        return new ResponseEntity<>(Message.success(contentVO), HttpStatus.OK);
    }
}
