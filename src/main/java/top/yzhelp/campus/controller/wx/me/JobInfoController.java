package top.yzhelp.campus.controller.wx.me;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.mail.MailUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import top.yzhelp.campus.controller.res.Result;
import top.yzhelp.campus.controller.wx.vo.Constants;
import top.yzhelp.campus.model.yh.JobInfo;
import top.yzhelp.campus.model.yh.WxUser;
import top.yzhelp.campus.service.JobInfoService;
import top.yzhelp.campus.service.MsgService;
import top.yzhelp.campus.service.WxUserService;
import top.yzhelp.campus.shiro.ShiroRealm;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="https://github.com/gongsir0630">码之泪殇</a>
 * @date 2021/4/26 11:49
 * 你的指尖,拥有改变世界的力量
 * @description description
 */
@RestController
@Slf4j
@RequestMapping("wx/job")
@Api(tags = "MINIAPP-工作信息接口")
public class JobInfoController {

  @Resource
  private JobInfoService jobInfoService;
  @Resource
  private WxUserService userService;
  @Autowired
  private StringRedisTemplate redisTemplate;
  @Resource
  private MsgService msgService;

  /**
   * 从认证信息中获取用户 openId
   * @return openId
   */
  private String getOpenId() {
    return ShiroRealm.getShiroAccount().getAuthName();
  }

  @GetMapping("/check")
  @ApiOperation("邮件确认")
  public String checkMail(@RequestParam("job_token_id")String job_token_id) {
    if (StrUtil.isBlank(job_token_id)) {
      return "check fail";
    }
    String openId = redisTemplate.opsForValue().get(job_token_id);
    if (StrUtil.isBlank(openId)) {
      return "check fail";
    }
    WxUser userInfo = this.userService.getUserInfo(openId);
    JobInfo jobInfo = this.jobInfoService.getJobInfoById(userInfo.getJobId());
    // 认证通过
    jobInfo.setStatus(Constants.CET_STATUS.get(2));
    this.jobInfoService.saveOrUpdate(jobInfo);
    // TODO: 向用户推送认证状态
    msgService.sendMsg("认证工作信息:"+jobInfo.getCompany().getName(), jobInfo.getStatus(),
      userInfo.getOpenId(), Constants.INFO_AUTH_ID);
    // 删除key
    redisTemplate.delete(job_token_id);
    return "success";
  }

  @GetMapping("/auth/mail")
  @ApiOperation("发送邮箱认证链接")
  @RequiresRoles("wx")
  public ResponseEntity<Result<?>> sendMail(String mail) {
    log.info("--->>>mail:[{}]",mail);
    WxUser userInfo = this.userService.getUserInfo(getOpenId());
    JobInfo jobInfo = jobInfoService.getJobInfoById(userInfo.getJobId());
    String job_token_id = UUID.randomUUID().toString();
    // 3天有效
    redisTemplate.opsForValue().set(job_token_id,getOpenId(),3, TimeUnit.DAYS);
    // 跳转链接
    String url = "https://yzhelp.top/wx/job/check?job_token_id="+job_token_id;
    // 当天日期
    String today = DateUtil.today();
    // 待审核
    jobInfo.setStatus(Constants.CET_STATUS.get(1));
    this.jobInfoService.saveOrUpdate(jobInfo);
    String content = "<p>亲爱的柚子帮同学:</p>"
      +"<p>你正在认证工作信息: "+jobInfo.getCompany().getName()+", 请在3天时间内确认邮箱地址以完成认证</p>"
      +"<p><a href='"+url+"'>点此确认邮箱地址</a></p>"
      +"<p>若链接点击无响应, 请复制以下链接在浏览器中打开, 验证后即可完成认证（<font color='red'>如非本人，请勿点击</font>）</p>"
      +"<a href='"+url+"'>"+url+"</a><br/><br/>"
      +"<p>西南石油大学-柚子帮</p>"
      +"<p>"+today+"</p>";
    MailUtil.send(mail,"柚子帮公司邮箱认证",content,true);
    return new ResponseEntity<>(Result.success(null), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  @RequiresRoles("wx")
  public ResponseEntity<Result<?>> getEduInfoById(@PathVariable int id) {
    return new ResponseEntity<>(Result.success(this.jobInfoService.getJobInfoById(id)), HttpStatus.OK);
  }
}
