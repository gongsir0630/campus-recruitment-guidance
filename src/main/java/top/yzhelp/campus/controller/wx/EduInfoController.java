package top.yzhelp.campus.controller.wx;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.mail.MailUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import top.yzhelp.campus.enums.CrgConstants;
import top.yzhelp.campus.model.base.EduInfo;
import top.yzhelp.campus.model.common.Message;
import top.yzhelp.campus.model.user.CrgWxUser;
import top.yzhelp.campus.service.EduInfoService;
import top.yzhelp.campus.service.MsgService;
import top.yzhelp.campus.service.WxUserService;
import top.yzhelp.campus.shiro.ShiroRealm;

/**
 * @author <a href="https://github.com/gongsir0630">Kyle</a>
 * @date 2021/4/26 11:49
 * @description description
 */
@RestController
@Slf4j
@RequestMapping("wx/edu")
@Api(tags = "MINIAPP-教育信息接口")
public class EduInfoController {

    @Resource
    private EduInfoService eduInfoService;
    @Resource
    private WxUserService userService;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Resource
    private MsgService msgService;

    /**
     * 从认证信息中获取用户 openId
     *
     * @return openId
     */
    private String getOpenId() {
        return ShiroRealm.getShiroAccount().getAuthName();
    }

    @GetMapping("/check")
    @ApiOperation("邮件确认")
    public String checkMail(@RequestParam("tokenId") String tokenId) {
        if (StrUtil.isBlank(tokenId)) {
            return "check fail";
        }
        String openId = redisTemplate.opsForValue().get(tokenId);
        if (StrUtil.isBlank(openId)) {
            return "check fail";
        }
        CrgWxUser userInfo = this.userService.getUserInfo(openId);
        EduInfo eduInfo = this.eduInfoService.getEduInfoById(userInfo.getEduId());
        // 认证通过
        eduInfo.setStatus(CrgConstants.CET_STATUS.get(2));
        this.eduInfoService.saveOrUpdate(eduInfo);
        // TODO: 向用户推送认证状态
        msgService.sendMsg("认证教育信息:" + eduInfo.getSchool().getName(), eduInfo.getStatus(),
            userInfo.getOpenId(), CrgConstants.INFO_AUTH_ID);
        // 删除key
        redisTemplate.delete(tokenId);
        return "success";
    }

    @GetMapping("/auth/mail")
    @ApiOperation("发送邮箱认证链接")
    @RequiresRoles("wx")
    public ResponseEntity<Message<?>> sendMail(String mail) {
        log.info("--->>>mail:[{}]", mail);
        CrgWxUser userInfo = this.userService.getUserInfo(getOpenId());
        EduInfo eduInfo = eduInfoService.getEduInfoById(userInfo.getEduId());
        String eduTokenId = UUID.randomUUID().toString();
        // 3天有效
        redisTemplate.opsForValue().set(eduTokenId, getOpenId(), 3, TimeUnit.DAYS);
        // 跳转链接
        String url = "https://yzhelp.top/wx/edu/check?edu_token_id=" + eduTokenId;
        // 当天日期
        String today = DateUtil.today();
        // 待审核
        eduInfo.setStatus(CrgConstants.CET_STATUS.get(1));
        this.eduInfoService.saveOrUpdate(eduInfo);
        String content = "<p>亲爱的柚子帮同学:</p>"
            + "<p>你正在认证教育信息: " + eduInfo.getSchool().getName() + ", 请在3天时间内确认邮箱地址以完成认证</p>"
            + "<p><a href='" + url + "'>点此确认邮箱地址</a></p>"
            + "<p>若链接点击无响应, 请复制以下链接在浏览器中打开, 验证后即可完成认证（<font color='red'>如非本人，请勿点击</font>）</p>"
            + "<a href='" + url + "'>" + url + "</a><br/><br/>"
            + "<p>西南石油大学-柚子帮</p>"
            + "<p>" + today + "</p>";
        MailUtil.send(mail, "柚子帮学校邮箱认证", content, true);
        return new ResponseEntity<>(Message.success(null), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ApiOperation("根据id获取教育信息详情")
    @RequiresRoles("wx")
    public ResponseEntity<Message<?>> getEduInfoById(@PathVariable int id) {
        return new ResponseEntity<>(Message.success(this.eduInfoService.getEduInfoById(id)), HttpStatus.OK);
    }
}
