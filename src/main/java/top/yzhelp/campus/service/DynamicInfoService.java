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
   * @param cur 当前页码
   * @param size 每页显示数量
   * @return IPage
   */
  IPage<DynamicInfo> getAllDtInfoList(long cur, long size);

  /**
   * 点赞动态
   * @param id 动态 id
   * @param openId 用户
   */
  void likeById(int id, String openId);

  /**
   * 动态详情
   * @param id 动态 id
   * @return 动态详情
   */
  DynamicInfo getDtDetailById(int id);

  /**
   * 收藏
   * @param id 动态 id
   * @param openId 用户
   */
  void collection(int id, String openId);

  /**
   * 删除动态
   * @param id 动态 id
   */
  void deleteDtById(int id);

  /**
   * 发布 | 更新
   * @param info 动态信息
   * @return 动态详情
   */
  DynamicInfo saveOrUpdateDt(DynamicInfo info);
}
