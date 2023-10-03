package top.yzhelp.campus.service;

import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;

import top.yzhelp.campus.model.user.AdminUser;

/**
 * @author <a href="https://github.com/gongsir0630">Kyle</a>
 * @date 2021/4/25 11:22
 * 你的指尖,拥有改变世界的力量
 * @description 管理员用户接口
 */
public interface AdminUserService extends IService<AdminUser> {
    /**
     * 密码登陆
     *
     * @param userId 用户名/手机号
     * @param password 密码
     * @return 登录信息
     */
    Map<String, String> loginByPass(String userId, String password);

    /**
     * 根据用户名或者手机号获取用户信息
     *
     * @param userId 用户名/手机号
     * @return AdminUser
     */
    AdminUser getByUserId(String userId);
}
