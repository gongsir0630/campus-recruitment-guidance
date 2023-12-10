package top.yzhelp.campus.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import top.yzhelp.campus.mapper.TagMapper;
import top.yzhelp.campus.model.Tag;
import top.yzhelp.campus.service.TagService;

/**
 * @author <a href="https://github.com/gongsir0630">Kyle</a>
 * @date 2021/4/8 15:56
 * @description 标签接口实现
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {
    /**
     * 根据 id 列表获取标签名称列表
     *
     * @param ids id 列表(eg: 1,2,3)
     * @return List<String>
     */
    @Override
    public List<String> getTagNameListByIds(String ids) {
        List<Tag> tags = this.list(new QueryWrapper<Tag>().in("id", Arrays.asList(ids.split(","))));
        return tags.stream().map(Tag::getTagName).collect(Collectors.toList());
    }

    /**
     * 根据 type 获取标签列表
     *
     * @param type 标签类型
     * @return 标签列表
     */
    @Override
    public List<Tag> getTagListByType(String type) {
        return this.list(new LambdaQueryWrapper<Tag>().eq(Tag::getTagType, type));
    }

    /**
     * 获取所有标签数据
     *
     * @return tags
     */
    @Override
    public List<Tag> getAllTags() {
        return this.list(new LambdaQueryWrapper<Tag>().orderByDesc(Tag::getId));
    }
}
