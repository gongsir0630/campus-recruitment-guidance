package top.yzhelp.campus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.yzhelp.campus.mapper.JobInfoMapper;
import top.yzhelp.campus.model.yh.JobInfo;
import top.yzhelp.campus.service.JobInfoService;

/**
 * @author <a href="https://github.com/gongsir0630">码之泪殇</a>
 * @date 2021/4/8 16:16
 * 你的指尖,拥有改变世界的力量
 * @description 用户工作信息接口实现
 */
@Service
public class JobInfoServiceImpl extends ServiceImpl<JobInfoMapper, JobInfo> implements JobInfoService {
  /**
   * 新增或更新工作信息
   *
   * @param jobInfo 工作信息
   * @return 工作信息
   */
  @Override
  public JobInfo saveOrUpdateJobInfo(JobInfo jobInfo) {
    this.saveOrUpdate(jobInfo);
    return this.getEduInfoById(jobInfo.getId());
  }

  /**
   * 根据 id 获取工作信息
   *
   * @param id 工作信息 id
   * @return 最新工作信息
   */
  @Override
  public JobInfo getEduInfoById(int id) {
    return this.getById(id);
  }
}
