package top.yzhelp.campus.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.yzhelp.campus.model.yh.School;

import java.util.List;

/**
 * @author <a href="https://github.com/gongsir0630">码之泪殇</a>
 * @date 2021/4/8 16:17
 * 你的指尖,拥有改变世界的力量
 * @description 学校信息接口
 */
public interface SchoolService extends IService<School> {
  /**
   * 获取学校列表
   * @return list
   */
  List<School> getAllList();
  /**
   * 新增或更新学校信息
   * @param school 学校信息
   * @return 学校信息
   */
  School saveOrUpdateSchool(School school);

  /**
   * 根据 id 获取学校信息
   * @param id 学校 id
   * @return 学校信息
   */
  School getSchoolById(int id);
}
