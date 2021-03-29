package top.yzhelp.campus.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.yzhelp.campus.model.WxUser;

import java.util.Map;

/**
 * @author <a href="https://github.com/gongsir0630">码之泪殇</a>
 * @date 2021/3/29 12:49
 * 你的指尖,拥有改变世界的力量
 * @description description
 */
public interface WxUserService extends IService<WxUser> {
  /**
   * 登录
   * @param jsCode 小程序code
   * @return 登录信息: 包含token
   */
  Map<String, String> login(String jsCode);
}
