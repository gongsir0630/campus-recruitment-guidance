package top.yzhelp.campus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.yzhelp.campus.mapper.SecretKeyMapper;
import top.yzhelp.campus.model.project.SecretKey;
import top.yzhelp.campus.service.SecretKeyService;

/**
 * @author <a href="https://github.com/gongsir0630">码之泪殇</a>
 * @date 2021/4/10 19:38
 * 你的指尖,拥有改变世界的力量
 * @description 秘钥信息接口实现
 */
@Service
public class SecretKeyServiceImpl extends ServiceImpl<SecretKeyMapper, SecretKey> implements SecretKeyService {
  /**
   * 通过平台名称获取 ak 和 sk
   *
   * @param name 名称
   * @return 秘钥信息
   */
  @Override
  public SecretKey getSkByPlatName(String name) {
    QueryWrapper<SecretKey> wrapper = new QueryWrapper<>();
    wrapper.eq("plat_name",name);
    return this.getOne(wrapper);
  }
}
