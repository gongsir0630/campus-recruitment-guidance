package top.yzhelp.campus.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

import top.yzhelp.campus.model.base.EduInfo;

/**
 * @author <a href="https://github.com/gongsir0630">Kyle</a>
 * @date 2021/4/8 16:12
 * @description 用户教育信息接口
 */
public interface EduInfoService extends IService<EduInfo> {
    /**
     * 新增或修改教育信息
     *
     * @param eduInfo 教育信息
     * @return 最新教育信息
     */
    EduInfo saveOrUpdateEduInfo(EduInfo eduInfo);

    /**
     * 获取教育信息
     *
     * @param id 教育信息 ID
     * @return 教育信息
     */
    EduInfo getEduInfoById(int id);

    /**
     * 根据 openId 获取教育信息
     *
     * @param openId openId
     * @return 最新教育信息
     */
    EduInfo getEduInfoByOpenId(String openId);

    /**
     * 获取所有数据
     *
     * @return list
     */
    List<EduInfo> getAllInfoList();
}
