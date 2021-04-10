package top.yzhelp.campus.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.yzhelp.campus.model.yh.EduInfo;
import top.yzhelp.campus.model.yh.JobInfo;
import top.yzhelp.campus.model.yh.WxUser;

import java.util.Map;

/**
 * @author <a href="https://github.com/gongsir0630">码之泪殇</a>
 * @date 2021/3/29 12:49
 * 你的指尖,拥有改变世界的力量
 * @description  小程序用户接口实现
 */
public interface WxUserService extends IService<WxUser> {
  /**
   * 登录
   * @param jsCode 小程序code
   * @return 登录信息: 包含token
   */
  Map<String, String> login(String jsCode);

  /**
   * 用户信息注册或者更新
   * @param user 小程序用户基本信息
   * @param eduInfo 小程序用户教育信息
   * @param jobInfo 小程序用户工作信息
   * @return 用户信息
   */
  WxUser saveOrUpdateUser(WxUser user, EduInfo eduInfo, JobInfo jobInfo);

  /**
   * 获取用户个人信息
   * @param openId 用户 Id
   * @return 用户信息
   */
  WxUser getUserInfo(String openId);
}
