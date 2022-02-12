package top.yzhelp.campus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import top.yzhelp.campus.mapper.CrgCompanyMapper;
import top.yzhelp.campus.model.CrgCompany;
import top.yzhelp.campus.service.CrgCompanyService;
import top.yzhelp.campus.vo.CrgCompanyVO;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author 码之泪殇 <gongsir0630@gmail.com>
 * Created on 2022-02-12
 */
@Lazy
@Service
public class CrgCompanyServiceImpl extends ServiceImpl<CrgCompanyMapper, CrgCompany>
        implements CrgCompanyService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public List<CrgCompanyVO> queryAll() {
        List<CrgCompany> crgCompanies = list();
        if (CollectionUtils.isEmpty(crgCompanies)) {
            return Collections.emptyList();
        }
        return crgCompanies.stream().filter(Objects::nonNull)
                .map(this::buildCrgCompanyVO).collect(Collectors.toList());
    }

    @Override
    public CrgCompanyVO queryById(long id) {
        CrgCompany company = getById(id);
        if (Objects.isNull(company)) {
            return null;
        }
        return buildCrgCompanyVO(company);
    }

    private CrgCompanyVO buildCrgCompanyVO(CrgCompany company) {
        return CrgCompanyVO.builder()
                .id(company.getId())
                .logo(company.getLogo())
                .name(company.getName())
                .slogan(company.getSlogan())
                .mailSuffix(company.getMailSuffix())
                .build();
    }
}
