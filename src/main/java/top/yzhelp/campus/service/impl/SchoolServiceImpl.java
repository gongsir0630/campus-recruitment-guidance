package top.yzhelp.campus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.yzhelp.campus.mapper.SchoolMapper;
import top.yzhelp.campus.model.yh.School;
import top.yzhelp.campus.service.SchoolService;

import java.util.List;

/**
 * @author <a href="https://github.com/gongsir0630">码之泪殇</a>
 * @date 2021/4/8 16:17
 * 你的指尖,拥有改变世界的力量
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
