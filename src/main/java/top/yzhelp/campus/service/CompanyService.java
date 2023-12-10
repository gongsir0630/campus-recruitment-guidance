package top.yzhelp.campus.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

import top.yzhelp.campus.model.base.Company;

/**
 * @author <a href="https://github.com/gongsir0630">Kyle</a>
 * @date 2021/4/8 16:09
 * @description 企业信息接口
 */
public interface CompanyService extends IService<Company> {
    /**
     * 新增或修改 Company 信息
     *
     * @param company 企业信息
     * @return 企业信息
     */
    Company saveOrUpdateCompany(Company company);

    /**
     * 根据企业 Id 获取企业信息
     *
     * @param id 企业 id
     * @return 企业信息
     */
    Company getCompanyById(int id);

    /**
     * 获取所有数据
     *
     * @return list
     */
    List<Company> getAllList();
}
