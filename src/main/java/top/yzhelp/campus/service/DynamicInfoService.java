package top.yzhelp.campus.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import top.yzhelp.campus.model.dt.DynamicInfo;

/**
 * @author <a href="https://github.com/gongsir0630">码之泪殇</a>
 * @date 2021/4/8 08:56
 * 你的指尖,拥有改变世界的力量
 * @description 小程序动态接口
 */
public interface DynamicInfoService extends IService<DynamicInfo> {
  /**
   * 获取所有动态列表
   * @param page 当前页码
   * @param size 每页显示数量
   * @param keyword 待检索关键字
   * @param topicTags 话题标签 Ids
   * @return IPage
   */
  IPage<DynamicInfo> getAllDtInfoList(int page, int size, String keyword, String topicTags);
}
