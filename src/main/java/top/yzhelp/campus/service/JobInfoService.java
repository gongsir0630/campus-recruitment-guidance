package top.yzhelp.campus.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

import top.yzhelp.campus.model.base.JobInfo;

/**
 * @author <a href="https://github.com/gongsir0630">Kyle</a>
 * @date 2021/4/8 16:15
 * 你的指尖,拥有改变世界的力量
 * @description 用户工作信息接口
 */
public interface JobInfoService extends IService<JobInfo> {
    /**
     * 新增或更新工作信息
     *
     * @param jobInfo 工作信息
     * @return 工作信息
     */
    JobInfo saveOrUpdateJobInfo(JobInfo jobInfo);

    /**
     * 根据 id 获取工作信息
     *
     * @param id 工作信息 id
     * @return 最新工作信息
     */
    JobInfo getJobInfoById(int id);

    /**
     * 根据 openId 获取工作信息
     *
     * @param openId openId
     * @return 最新工作信息
     */
    JobInfo getJobInfoByOpenId(String openId);

    /**
     * 获取所有数据
     *
     * @return list
     */
    List<JobInfo> getAllInfoList();
}
