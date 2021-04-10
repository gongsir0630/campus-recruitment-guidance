package top.yzhelp.campus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.yzhelp.campus.mapper.EduInfoMapper;
import top.yzhelp.campus.model.yh.EduInfo;
import top.yzhelp.campus.service.EduInfoService;

import javax.annotation.Resource;

/**
 * @author <a href="https://github.com/gongsir0630">码之泪殇</a>
 * @date 2021/4/8 16:13
 * 你的指尖,拥有改变世界的力量
 * @description 用户教育信息接口实现
 */
@Service
@Slf4j
public class EduInfoServiceImpl extends ServiceImpl<EduInfoMapper, EduInfo> implements EduInfoService {
  /**
   * 新增或修改教育信息
   *
   * @param eduInfo 教育信息
   * @return 最新教育信息
   */
  @Override
  public EduInfo saveOrUpdateEduInfo(EduInfo eduInfo) {
    this.saveOrUpdate(eduInfo);
    return this.getEduInfoById(eduInfo.getId());
  }

  /**
   * 获取教育信息
   *
   * @param id 教育信息 ID
   * @return 教育信息
   */
  @Override
  public EduInfo getEduInfoById(int id) {
    return this.getById(id);
  }
}
