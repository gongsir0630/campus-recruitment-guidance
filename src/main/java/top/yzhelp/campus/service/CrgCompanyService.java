package top.yzhelp.campus.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

import top.yzhelp.campus.model.basic.CrgCompany;
import top.yzhelp.campus.vo.CrgCompanyVO;

/**
 * @author kyle <gongsir0630@gmail.com>
 * Created on 2022-02-12
 */
public interface CrgCompanyService extends IService<CrgCompany> {

    /**
     * 查询所有
     *
     * @return List<CrgCompanyVO>
     */
    List<CrgCompanyVO> queryAll();

    /**
     * 根据 id 查询
     *
     * @param id 主键id
     * @return CrgCompanyVO
     */
    CrgCompanyVO queryById(long id);
}
