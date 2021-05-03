package top.yzhelp.campus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.yzhelp.campus.mapper.EduInfoMapper;
import top.yzhelp.campus.model.yh.EduInfo;
import top.yzhelp.campus.service.EduInfoService;
import top.yzhelp.campus.service.SchoolService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author <a href="https://github.com/gongsir0630">码之泪殇</a>
 * @date 2021/4/8 16:13
 * 你的指尖,拥有改变世界的力量
 * @description 用户教育信息接口实现
 */
@Service
@Slf4j
public class EduInfoServiceImpl extends ServiceImpl<EduInfoMapper, EduInfo> implements EduInfoService {
  @Resource
  private SchoolService schoolService;
  /**
   * 新增或修改教育信息
   *
   * @param eduInfo 教育信息
   * @return 最新教育信息
   */
  @Override
  public EduInfo saveOrUpdateEduInfo(EduInfo eduInfo) {
    if (eduInfo.getId() != null) {
      // 教育信息已经存在，如果信息发生更新，则需要重新审核
      EduInfo dbInfo = this.getEduInfoById(eduInfo.getId());
      if (!dbInfo.equals(eduInfo)) {
        eduInfo.setStatus(Boolean.toString(false));
      }
    } else {
      // 教育信息不存在，需要审核
      eduInfo.setStatus(Boolean.toString(false));
    }
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
    EduInfo eduInfo = this.getById(id);
    eduInfo.setSchool(this.schoolService.getSchoolById(eduInfo.getSchoolId()));
    return eduInfo;
  }

  /**
   * 根据 openId 获取教育信息
   *
   * @param openId openId
   * @return 最新教育信息
   */
  @Override
  public EduInfo getEduInfoByOpenId(String openId) {
    EduInfo eduInfo = this.getOne(new QueryWrapper<EduInfo>().eq("open_id", openId));
    eduInfo.setSchool(this.schoolService.getSchoolById(eduInfo.getSchoolId()));
    return eduInfo;
  }

  @Override
  public List<EduInfo> getAllInfoList() {
    List<EduInfo> list = this.list(new LambdaQueryWrapper<EduInfo>().orderByDesc(EduInfo::getId));
    list.forEach(eduInfo -> eduInfo.setSchool(this.schoolService.getSchoolById(eduInfo.getSchoolId())));
    return list;
  }
}
