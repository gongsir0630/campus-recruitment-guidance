package top.yzhelp.campus.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.yzhelp.campus.mapper.DynamicInfoMapper;
import top.yzhelp.campus.model.dt.DynamicInfo;
import top.yzhelp.campus.service.DynamicInfoService;
import top.yzhelp.campus.service.WxUserService;

import javax.annotation.Resource;

/**
 * @author <a href="https://github.com/gongsir0630">码之泪殇</a>
 * @date 2021/4/8 08:58
 * 你的指尖,拥有改变世界的力量
 * @description 小程序动态功能模块 -> 接口实现
 */
@Service
public class DynamicInfoServiceImpl extends ServiceImpl<DynamicInfoMapper, DynamicInfo> implements DynamicInfoService {
  @Resource
  private WxUserService wxUserService;

  /**
   * 获取所有动态列表
   *
   * @param page    当前页码
   * @param size    每页显示数量
   * @param keyword 待检索关键字
   * @param topicTags 话题标签 Ids
   * @return IPage
   */
  @Override
  public IPage<DynamicInfo> getAllDtInfoList(int page, int size, String keyword, String topicTags) {
    return null;
  }
}
