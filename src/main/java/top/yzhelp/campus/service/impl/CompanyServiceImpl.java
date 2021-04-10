package top.yzhelp.campus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.yzhelp.campus.mapper.CompanyMapper;
import top.yzhelp.campus.model.yh.Company;
import top.yzhelp.campus.service.CompanyService;

/**
 * @author <a href="https://github.com/gongsir0630">码之泪殇</a>
 * @date 2021/4/8 16:10
 * 你的指尖,拥有改变世界的力量
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
}
