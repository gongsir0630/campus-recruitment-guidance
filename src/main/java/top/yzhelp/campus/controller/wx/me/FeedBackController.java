package top.yzhelp.campus.controller.wx.me;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yzhelp.campus.controller.res.Result;
import top.yzhelp.campus.controller.wx.vo.Constants;
import top.yzhelp.campus.model.other.FeedBack;
import top.yzhelp.campus.service.FeedBackService;
import top.yzhelp.campus.shiro.ShiroRealm;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author <a href="https://github.com/gongsir0630">码之泪殇</a>
 * @date 2021/4/26 11:49
 * 你的指尖,拥有改变世界的力量
 * @description 小程序反馈接口
 */
@RestController
@Slf4j
@RequestMapping("wx/feed")
@Api(tags = "MINIAPP-小程序反馈接口")
public class FeedBackController {

  @Resource
  private FeedBackService feedBackService;

  /**
   * 当前用户的用户ID
   * @return openId
   */
  private String getOpenId() {
    return ShiroRealm.getShiroAccount().getAuthName();
  }

  @PutMapping
  @ApiOperation("用户反馈")
  @RequiresRoles("wx")
  public ResponseEntity<Result<?>> add(FeedBack back) {
    back.setFeedTime(new Date());
    back.setAcceptStatus(Constants.ACCEPT_STATUS.get(0));
    // 当前用户
    back.setOpenId(getOpenId());
    this.feedBackService.save(back);
    return new ResponseEntity<>(Result.success(null), HttpStatus.OK);
  }

  @GetMapping("/me")
  @ApiOperation("获取当前用户的所有反馈")
  @RequiresRoles("wx")
  public ResponseEntity<Result<?>> list() {
    List<FeedBack> feedBacks = this.feedBackService.getFeedBackByOpenId(getOpenId());
    return new ResponseEntity<>(Result.success(feedBacks), HttpStatus.OK);
  }
}
