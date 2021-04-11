package top.yzhelp.campus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.yzhelp.campus.mapper.TagMapper;
import top.yzhelp.campus.model.other.Tag;
import top.yzhelp.campus.service.TagService;

/**
 * @author <a href="https://github.com/gongsir0630">码之泪殇</a>
 * @date 2021/4/8 15:56
 * 你的指尖,拥有改变世界的力量
 * @description 标签接口实现
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {
}