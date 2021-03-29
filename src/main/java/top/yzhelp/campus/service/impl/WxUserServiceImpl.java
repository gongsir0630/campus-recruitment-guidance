package top.yzhelp.campus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.yzhelp.campus.mapper.WxUserMapper;
import top.yzhelp.campus.model.WxUser;
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
  private WxUserMapper wxUserMapper;
  @Resource
  private JwtUtil jwtUtil;
  @Resource
  private WxService wxService;

  @Override
  public Map<String, String> login(String jsCode) {
    Map<String, String> res = new HashMap<>();
    ShiroAccount shiroAccount = wxService.login(jsCode);
    log.info("--->>>shiroAccount信息:[{}]",shiroAccount);
    WxUser user = wxUserMapper.selectById(shiroAccount.getAuthName());
    if (user == null) {
      // 用户不存在, 提醒用户提交注册信息
      res.put("canLogin",Boolean.FALSE.toString());
    } else {
      res.put("canLogin",Boolean.TRUE.toString());
    }
    res.put("token", jwtUtil.sign(shiroAccount));
    return res;
  }
}
