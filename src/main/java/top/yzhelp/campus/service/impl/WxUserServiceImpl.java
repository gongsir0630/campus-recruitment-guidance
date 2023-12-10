package top.yzhelp.campus.service.impl;

import java.util.Objects;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import lombok.extern.slf4j.Slf4j;
import top.yzhelp.campus.enums.WebResultCode;
import top.yzhelp.campus.exception.ApiAuthException;
import top.yzhelp.campus.mapper.WxUserMapper;
import top.yzhelp.campus.model.base.EduInfo;
import top.yzhelp.campus.model.base.JobInfo;
import top.yzhelp.campus.model.user.CrgWxUser;
import top.yzhelp.campus.service.EduInfoService;
import top.yzhelp.campus.service.JobInfoService;
import top.yzhelp.campus.service.WxUserService;
import top.yzhelp.campus.shiro.vo.ShiroAccount;
import top.yzhelp.campus.util.JwtUtil;
import top.yzhelp.campus.wx.service.WxService;

/**
 * @author <a href="https://github.com/gongsir0630">Kyle</a>
 * @date 2021/3/29 12:49
 * @description 小程序用户业务逻辑实现
 */
@Slf4j
@Lazy
@Service
public class WxUserServiceImpl extends ServiceImpl<WxUserMapper, CrgWxUser> implements WxUserService {

    @Resource
    private EduInfoService eduInfoService;
    @Resource
    private JobInfoService jobInfoService;

    @Resource
    private JwtUtil jwtUtil;
    @Resource
    private WxService wxService;

    /**
     * 登录
     *
     * @param jsCode 小程序code
     * @return 登录信息: 包含token
     */
    @Override
    public String login(String jsCode) {
        ShiroAccount shiroAccount = wxService.login(jsCode);
        log.info("login, jsCode=>{}, account=>{}", jsCode, JSON.toJSONString(shiroAccount));
        CrgWxUser user = this.getUserInfo(shiroAccount.getAuthName());
        if (Objects.isNull(user)) {
            return StringUtils.EMPTY;
        }
        return jwtUtil.sign(shiroAccount);
    }

    /**
     * 用户信息注册或者更新
     *
     * @param user 小程序用户基本信息
     * @param eduInfo 小程序用户教育信息
     * @param jobInfo 小程序用户工作信息
     * @return 用户信息
     */
    @Override
    public CrgWxUser saveOrUpdateUser(CrgWxUser user, EduInfo eduInfo, JobInfo jobInfo) {
        CrgWxUser userInfo = this.getUserInfo(user.getOpenId());
        if (null != userInfo) {
            // 用户已注册，更新信息
            eduInfo.setId(userInfo.getEduId());
            if (userInfo.getJobId() != 0) {
                jobInfo.setId(userInfo.getJobId());
            }
        }
        // todo: 保存教育信息
        Integer eduId = eduInfoService.saveOrUpdateEduInfo(eduInfo).getId();
        // todo: 保存工作信息
        Integer jobId = 0;
        if (jobInfo != null) {
            jobId = jobInfoService.saveOrUpdateJobInfo(jobInfo).getId();
        }
        // todo: 用户注册
        user.setEduId(eduId);
        user.setJobId(jobId);
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
    public CrgWxUser getUserInfo(String openId) {
        if (StringUtils.isBlank(openId)) {
            log.info("openIs is empty!");
            throw new ApiAuthException(WebResultCode.LOGIN_FAIL);
        }
        CrgWxUser wxUser = this.getById(openId);
        if (wxUser != null) {
            fillBasicInfo(wxUser);
        }
        return wxUser;
    }

    private void fillBasicInfo(CrgWxUser wxUser) {
        if (wxUser == null) {
            return;
        }
        wxUser.setEduInfo(this.eduInfoService.getEduInfoById(wxUser.getEduId()));
        wxUser.setJobInfo(this.jobInfoService.getJobInfoById(wxUser.getJobId()));
    }
}
