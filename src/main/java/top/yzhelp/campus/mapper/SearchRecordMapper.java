package top.yzhelp.campus.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import top.yzhelp.campus.model.SearchRecord;

/**
 * @author <a href="https://github.com/gongsir0630">Kyle</a>
 * @date 2021/4/8 15:53
 * @description 搜索记录 Mapper
 */
@Mapper
public interface SearchRecordMapper extends BaseMapper<SearchRecord> {
}
