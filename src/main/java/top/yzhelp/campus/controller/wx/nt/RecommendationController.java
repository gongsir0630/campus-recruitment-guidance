package top.yzhelp.campus.controller.wx.nt;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import top.yzhelp.campus.controller.res.Result;
import top.yzhelp.campus.model.nt.Recommendation;
import top.yzhelp.campus.service.RecommendationService;
import top.yzhelp.campus.service.TagService;
import top.yzhelp.campus.service.WxUserService;
import top.yzhelp.campus.shiro.ShiroRealm;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author <a href="https://github.com/gongsir0630">码之泪殇</a>
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
   * @return openId
   */
  private String getOpenId() {
    return ShiroRealm.getShiroAccount().getAuthName();
  }

  @DeleteMapping("/{id}")
  @ApiOperation("删除")
  @RequiresRoles("wx")
  public ResponseEntity<Result<?>> deleteById(@PathVariable int id) {
    this.recommendationService.removeById(id);
    return new ResponseEntity<>(Result.success(null),HttpStatus.OK);
  }

  @PostMapping
  @ApiOperation("发布内推 | 更新")
  @RequiresRoles("wx")
  public ResponseEntity<Result<?>> saveOrUpdate(Recommendation info) {
    info.setOpenId(this.getOpenId());
    info.setPublishTime(new Date());
    Recommendation recommendation = this.recommendationService.saveOrUpdateInfo(info);
    recommendation.setUser(this.userService.getUserInfo(recommendation.getOpenId()));
    return new ResponseEntity<>(Result.success(recommendation),HttpStatus.OK);
  }

  @GetMapping("list/{type}")
  public ResponseEntity<Result<?>> all (@PathVariable Integer type) {
    type = type == null ? 0 : type;
    List<Recommendation> list = this.recommendationService.list(
      new LambdaQueryWrapper<Recommendation>().orderByDesc(Recommendation::getId));
    list.forEach(recommendation -> {
      recommendation.setUser(this.userService.getUserInfo(recommendation.getOpenId()));
      // 转换标签列表
      List<String> tags = recommendation.getPositionTags() != null
        ? tagService.getTagNameListByIds(recommendation.getPositionTags())
        : new ArrayList<>();
      recommendation.setPositionTags(String.join(",",tags));
    });
    return new ResponseEntity<>(Result.success(list), HttpStatus.OK);
  }
}
