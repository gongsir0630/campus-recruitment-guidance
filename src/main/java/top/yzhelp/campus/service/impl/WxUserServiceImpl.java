package top.yzhelp.campus.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.yzhelp.campus.mapper.WxUserMapper;
import top.yzhelp.campus.model.other.Content;
import top.yzhelp.campus.model.yh.EduInfo;
import top.yzhelp.campus.model.yh.JobInfo;
import top.yzhelp.campus.model.yh.WxUser;
import top.yzhelp.campus.service.ContentService;
import top.yzhelp.campus.service.EduInfoService;
import top.yzhelp.campus.service.JobInfoService;
import top.yzhelp.campus.service.WxUserService;
import top.yzhelp.campus.shiro.vo.ShiroAccount;
import top.yzhelp.campus.util.JwtUtil;
import top.yzhelp.campus.wx.service.WxService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="https://github.com/gongsir0630">码之泪殇</a>
 * @date 2021/3/29 12:49
 * 你的指尖,拥有改变世界的力量
 * @description 小程序用户业务逻辑实现
 */
@Service
@Slf4j
public class WxUserServiceImpl extends ServiceImpl<WxUserMapper, WxUser> implements WxUserService {

  @Resource
  private EduInfoService eduInfoService;
  @Resource
  private JobInfoService jobInfoService;
  @Resource
  private ContentService contentService;

  @Resource
  private JwtUtil jwtUtil;
  @Resource
  private WxService wxService;

  /**
   * 登录
   * @param jsCode 小程序code
   * @return 登录信息: 包含token
   */
  @Override
  public Map<String, String> login(String jsCode) {
    Map<String, String> res = new HashMap<>();
    ShiroAccount shiroAccount = wxService.login(jsCode);
    log.info("--->>>shiroAccount信息:[{}]",shiroAccount);
    WxUser user = this.getById(shiroAccount.getAuthName());
    if (user == null) {
      // 用户不存在, 提醒用户提交注册信息
      res.put("canLogin",Boolean.FALSE.toString());
    } else {
      res.put("canLogin",Boolean.TRUE.toString());
    }
    res.put("token", jwtUtil.sign(shiroAccount));
    return res;
  }

  /**
   * 用户信息注册或者更新
   * @param user 小程序用户基本信息
   * @param eduInfo 小程序用户教育信息
   * @param jobInfo 小程序用户工作信息
   * @return 用户信息
   */
  @Override
  public WxUser saveOrUpdateUser(WxUser user, EduInfo eduInfo, JobInfo jobInfo) {
    WxUser userInfo = this.getUserInfo(user.getOpenId());
    if (null != userInfo) {
      eduInfo.setId(userInfo.getEduId());
      jobInfo.setId(userInfo.getJobId());
    }
    // todo: 保存教育信息
    Integer eduId = eduInfoService.saveOrUpdateEduInfo(eduInfo).getId();
    // todo: 保存工作信息
    Integer jobId = jobInfoService.saveOrUpdateJobInfo(jobInfo).getId();
    // todo: 用户注册
    user.setEduId(eduId);
    user.setJobId(jobId);
    Content myContent = this.contentService.getMyContent(user.getOpenId());
    if (myContent == null) {
      myContent = new Content();
      myContent.setOpenId(user.getOpenId());
      this.contentService.saveOrUpdate(myContent);
    }
    this.saveOrUpdate(user);
    return this.getUserInfo(user.getOpenId());
  }

  /**
   * 获取用户个人基本信息
   *
   * @param openId 用户 Id
   * @return 用户信息
   */
  @Override
  public WxUser getUserInfo(String openId) {
    Assert.notBlank(openId,"--->>>openID is null");
    return this.getById(openId);
  }
}
