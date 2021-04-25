package top.yzhelp.campus.service.impl;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.yzhelp.campus.mapper.AdminUserMapper;
import top.yzhelp.campus.model.admin.AdminUser;
import top.yzhelp.campus.service.AdminUserService;
import top.yzhelp.campus.shiro.vo.ShiroAccount;
import top.yzhelp.campus.util.JwtUtil;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Map;

/**
 * @author <a href="https://github.com/gongsir0630">码之泪殇</a>
 * @date 2021/4/25 11:23
 * 你的指尖,拥有改变世界的力量
 * @description 管理员用户接口实现
 */
@Service
public class AdminUserServiceImpl extends ServiceImpl<AdminUserMapper, AdminUser> implements AdminUserService {

  @Resource
  private JwtUtil jwtUtil;

  /**
   * 密码登陆
   *
   * @param userId   用户名
   * @param password 密码
   * @return 登录信息
   */
  @Override
  public Map<String, String> loginByPass(String userId, String password) {
    Map<String,String> result = MapUtil.newHashMap();
    ShiroAccount account = new ShiroAccount(userId,password, Collections.singletonList(JwtUtil.ROLE_ADMIN));
    AdminUser admin = this.getByUserId(userId);
    if (null == admin) {
      // 用户不存在
      result.put("canLogin",Boolean.FALSE.toString());
    } else {
      if (!password.equals(admin.getPassword())) {
        // 密码错误
        result.put("canLogin",Boolean.FALSE.toString());
      } else {
        result.put("canLogin",Boolean.TRUE.toString());
      }
    }
    result.put("token",jwtUtil.sign(account));
    return result;
  }

  /**
   * 根据用户名获取用户信息
   *
   * @param userId 用户名/手机号
   * @return AdminUser
   */
  @Override
  public AdminUser getByUserId(String userId) {
    return this.getOne(new LambdaQueryWrapper<AdminUser>()
      .eq(AdminUser::getUserId,userId)
      .or()
      .eq(AdminUser::getPhoneNumber,userId));
  }
}
