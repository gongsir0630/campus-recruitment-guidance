package top.yzhelp.campus.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import top.yzhelp.campus.model.base.Company;

/**
 * @author <a href="https://github.com/gongsir0630">Kyle</a>
 * @date 2021/4/8 16:08
 * 你的指尖,拥有改变世界的力量
 * @description 企业信息 Mapper
 */
@Mapper
public interface CompanyMapper extends BaseMapper<Company> {
}
