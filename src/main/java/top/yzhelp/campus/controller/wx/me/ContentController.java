package top.yzhelp.campus.controller.wx.me;

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
import top.yzhelp.campus.model.other.Content;
import top.yzhelp.campus.service.ContentService;
import top.yzhelp.campus.shiro.ShiroRealm;

import javax.annotation.Resource;

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
  private ContentService contentService;

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
    Content content = this.contentService.getById(getOpenId());
    return new ResponseEntity<>(Result.success(content), HttpStatus.OK);
  }
}
