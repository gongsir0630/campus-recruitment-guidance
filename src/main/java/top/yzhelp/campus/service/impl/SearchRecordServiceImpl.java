package top.yzhelp.campus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.yzhelp.campus.mapper.SearchRecordMapper;
import top.yzhelp.campus.model.other.SearchRecord;
import top.yzhelp.campus.service.SearchRecordService;

/**
 * @author <a href="https://github.com/gongsir0630">码之泪殇</a>
 * @date 2021/4/8 15:54
 * 你的指尖,拥有改变世界的力量
 * @description 搜索历史接口实现
 */
@Service
public class SearchRecordServiceImpl extends ServiceImpl<SearchRecordMapper, SearchRecord> implements SearchRecordService {
}
