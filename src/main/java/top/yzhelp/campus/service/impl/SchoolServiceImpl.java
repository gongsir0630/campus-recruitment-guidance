package top.yzhelp.campus.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import top.yzhelp.campus.mapper.SchoolMapper;
import top.yzhelp.campus.model.base.School;
import top.yzhelp.campus.service.SchoolService;

/**
 * @author <a href="https://github.com/gongsir0630">Kyle</a>
 * @date 2021/4/8 16:17
 * @description 学校信息街口实现
 */
@Service
public class SchoolServiceImpl extends ServiceImpl<SchoolMapper, School> implements SchoolService {
    @Override
    public List<School> getAllList() {
        return this.list();
    }

    /**
     * 新增或更新学校信息
     *
     * @param school 学校信息
     * @return 学校信息
     */
    @Override
    public School saveOrUpdateSchool(School school) {
        this.saveOrUpdate(school);
        return this.getSchoolById(school.getId());
    }

    /**
     * 根据 id 获取学校信息
     *
     * @param id 学校 id
     * @return 学校信息
     */
    @Override
    public School getSchoolById(int id) {
        return this.getById(id);
    }
}
