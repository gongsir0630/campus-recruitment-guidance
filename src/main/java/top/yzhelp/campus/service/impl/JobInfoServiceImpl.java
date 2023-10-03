package top.yzhelp.campus.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import top.yzhelp.campus.mapper.JobInfoMapper;
import top.yzhelp.campus.model.base.JobInfo;
import top.yzhelp.campus.service.CompanyService;
import top.yzhelp.campus.service.JobInfoService;

/**
 * @author <a href="https://github.com/gongsir0630">Kyle</a>
 * @date 2021/4/8 16:16
 * 你的指尖,拥有改变世界的力量
 * @description 用户工作信息接口实现
 */
@Service
public class JobInfoServiceImpl extends ServiceImpl<JobInfoMapper, JobInfo> implements JobInfoService {
    @Resource
    private CompanyService companyService;

    /**
     * 新增或更新工作信息
     *
     * @param jobInfo 工作信息
     * @return 工作信息
     */
    @Override
    public JobInfo saveOrUpdateJobInfo(JobInfo jobInfo) {
        if (jobInfo.getId() != null) {
            // 工作信息已经存在，如果信息发生更新，则需要重新审核
            JobInfo dbInfo = this.getJobInfoById(jobInfo.getId());
            if (!dbInfo.equals(jobInfo)) {
                jobInfo.setStatus(Boolean.toString(false));
            }
        } else {
            // 工作信息不存在，需要审核
            jobInfo.setStatus(Boolean.toString(false));
        }
        this.saveOrUpdate(jobInfo);
        return this.getJobInfoById(jobInfo.getId());
    }

    /**
     * 根据 id 获取工作信息
     *
     * @param id 工作信息 id
     * @return 最新工作信息
     */
    @Override
    public JobInfo getJobInfoById(int id) {
        JobInfo jobInfo = this.getById(id);
        if (jobInfo != null) {
            jobInfo.setCompany(this.companyService.getCompanyById(jobInfo.getCompanyId()));
        }
        return jobInfo;
    }

    /**
     * 根据 openId 获取工作信息
     *
     * @param openId openId
     * @return 最新工作信息
     */
    @Override
    public JobInfo getJobInfoByOpenId(String openId) {
        JobInfo jobInfo = this.getOne(new QueryWrapper<JobInfo>().eq("open_id", openId));
        if (jobInfo != null) {
            jobInfo.setCompany(this.companyService.getCompanyById(jobInfo.getCompanyId()));
        }
        return jobInfo;
    }

    @Override
    public List<JobInfo> getAllInfoList() {
        List<JobInfo> list = this.list(new LambdaQueryWrapper<JobInfo>().orderByDesc(JobInfo::getId));
        list.forEach(eduInfo -> eduInfo.setCompany(this.companyService.getCompanyById(eduInfo.getCompanyId())));
        return list;
    }
}
