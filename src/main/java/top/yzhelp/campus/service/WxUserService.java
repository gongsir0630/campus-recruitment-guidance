package top.yzhelp.campus.service;

import com.baomidou.mybatisplus.extension.service.IService;

import top.yzhelp.campus.model.base.EduInfo;
import top.yzhelp.campus.model.base.JobInfo;
import top.yzhelp.campus.model.user.CrgWxUser;

/**
 * @author <a href="https://github.com/gongsir0630">Kyle</a>
 * @date 2021/3/29 12:49
 * @description 小程序用户接口实现
 */
public interface WxUserService extends IService<CrgWxUser> {
    /**
     * 登录
     *
     * @param jsCode 小程序code
     * @return 登录信息: 包含token
     */
    String login(String jsCode);

    /**
     * 用户信息注册或者更新
     *
     * @param user 小程序用户基本信息
     * @param eduInfo 小程序用户教育信息
     * @param jobInfo 小程序用户工作信息
     * @return 用户信息
     */
    CrgWxUser saveOrUpdateUser(CrgWxUser user, EduInfo eduInfo, JobInfo jobInfo);

    /**
     * 获取用户个人信息
     *
     * @param openId 用户 Id
     * @return 用户信息
     */
    CrgWxUser getUserInfo(String openId);
}
