package top.yzhelp.campus.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import top.yzhelp.campus.mapper.CompanyMapper;
import top.yzhelp.campus.model.base.Company;
import top.yzhelp.campus.service.CompanyService;

/**
 * @author <a href="https://github.com/gongsir0630">Kyle</a>
 * @date 2021/4/8 16:10
 * @description 企业信息接口实现
 */
@Service
public class CompanyServiceImpl extends ServiceImpl<CompanyMapper, Company> implements CompanyService {
    /**
     * 新增或修改 Company 信息
     *
     * @param company 企业信息
     * @return 企业信息
     */
    @Override
    public Company saveOrUpdateCompany(Company company) {
        this.saveOrUpdate(company);
        return this.getCompanyById(company.getId());
    }

    /**
     * 根据企业 Id 获取企业信息
     *
     * @param id 企业 id
     * @return 企业信息
     */
    @Override
    public Company getCompanyById(int id) {
        return this.getById(id);
    }

    @Override
    public List<Company> getAllList() {
        return this.list();
    }
}
