package top.yzhelp.campus.controller.wx;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import top.yzhelp.campus.enums.CrgConstants;
import top.yzhelp.campus.model.FeedBack;
import top.yzhelp.campus.model.common.Message;
import top.yzhelp.campus.service.FeedBackService;
import top.yzhelp.campus.shiro.ShiroRealm;

/**
 * @author <a href="https://github.com/gongsir0630">Kyle</a>
 * @date 2021/4/26 11:49
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
     *
     * @return openId
     */
    private String getOpenId() {
        return ShiroRealm.getShiroAccount().getAuthName();
    }

    @PutMapping
    @ApiOperation("用户反馈")
    @RequiresRoles("wx")
    public ResponseEntity<Message<?>> add(FeedBack back) {
        back.setFeedTime(new Date());
        back.setAcceptStatus(CrgConstants.ACCEPT_STATUS.get(0));
        // 当前用户
        back.setOpenId(getOpenId());
        this.feedBackService.save(back);
        return new ResponseEntity<>(Message.success(null), HttpStatus.OK);
    }

    @GetMapping("/me")
    @ApiOperation("获取当前用户的所有反馈")
    @RequiresRoles("wx")
    public ResponseEntity<Message<?>> list() {
        List<FeedBack> feedBacks = this.feedBackService.getFeedBackByOpenId(getOpenId());
        return new ResponseEntity<>(Message.success(feedBacks), HttpStatus.OK);
    }
}
