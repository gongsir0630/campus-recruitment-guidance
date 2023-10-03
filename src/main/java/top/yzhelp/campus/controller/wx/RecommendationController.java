package top.yzhelp.campus.controller.wx;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import top.yzhelp.campus.model.Recommendation;
import top.yzhelp.campus.model.common.Message;
import top.yzhelp.campus.service.RecommendationService;
import top.yzhelp.campus.service.TagService;
import top.yzhelp.campus.service.WxUserService;
import top.yzhelp.campus.shiro.ShiroRealm;

/**
 * @author <a href="https://github.com/gongsir0630">Kyle</a>
 * @date 2021/4/14 16:32
 * 你的指尖,拥有改变世界的力量
 * @description 内推信息接口
 */
@RestController
@Slf4j
@RequestMapping("wx/nt")
@Api(tags = "MINIAPP-小程序内推模块接口")
public class RecommendationController {
    @Resource
    private RecommendationService recommendationService;
    @Resource
    private WxUserService userService;
    @Resource
    private TagService tagService;

    /**
     * 从认证信息中获取用户 openId
     *
     * @return openId
     */
    private String getOpenId() {
        return ShiroRealm.getShiroAccount().getAuthName();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除")
    @RequiresRoles("wx")
    public ResponseEntity<Message<?>> deleteById(@PathVariable int id) {
        this.recommendationService.removeById(id);
        return new ResponseEntity<>(Message.success(null), HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation("发布内推 | 更新")
    @RequiresRoles("wx")
    public ResponseEntity<Message<?>> saveOrUpdate(Recommendation info) {
        info.setOpenId(this.getOpenId());
        info.setPublishTime(new Date());
        Recommendation recommendation = this.recommendationService.saveOrUpdateInfo(info);
        recommendation.setUser(this.userService.getUserInfo(recommendation.getOpenId()));
        return new ResponseEntity<>(Message.success(recommendation), HttpStatus.OK);
    }

    @GetMapping("list/{type}")
    public ResponseEntity<Message<?>> all(@PathVariable Integer type) {
        type = type == null ? 0 : type;
        List<Recommendation> list = this.recommendationService.list(
            new LambdaQueryWrapper<Recommendation>().orderByDesc(Recommendation::getId));
        list.forEach(recommendation -> {
            recommendation.setUser(this.userService.getUserInfo(recommendation.getOpenId()));
            // 转换标签列表
            List<String> tags = recommendation.getPositionTags() != null
                                ? tagService.getTagNameListByIds(recommendation.getPositionTags())
                                : new ArrayList<>();
            recommendation.setPositionTags(String.join(",", tags));
        });
        return new ResponseEntity<>(Message.success(list), HttpStatus.OK);
    }
}
