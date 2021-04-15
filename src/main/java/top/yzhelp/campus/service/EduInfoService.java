package top.yzhelp.campus.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.yzhelp.campus.model.yh.EduInfo;
import top.yzhelp.campus.model.yh.JobInfo;

/**
 * @author <a href="https://github.com/gongsir0630">码之泪殇</a>
 * @date 2021/4/8 16:12
 * 你的指尖,拥有改变世界的力量
 * @description 用户教育信息接口
 */
public interface EduInfoService extends IService<EduInfo> {
  /**
   * 新增或修改教育信息
   * @param eduInfo 教育信息
   * @return 最新教育信息
   */
  EduInfo saveOrUpdateEduInfo(EduInfo eduInfo);

  /**
   * 获取教育信息
   * @param id 教育信息 ID
   * @return 教育信息
   */
  EduInfo getEduInfoById(int id);

  /**
   * 根据 openId 获取教育信息
   * @param openId openId
   * @return 最新教育信息
   */
  EduInfo getEduInfoByOpenId(String openId);

}
