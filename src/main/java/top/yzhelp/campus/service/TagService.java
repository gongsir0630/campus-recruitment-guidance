package top.yzhelp.campus.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

import top.yzhelp.campus.model.Tag;

/**
 * @author <a href="https://github.com/gongsir0630">Kyle</a>
 * @date 2021/4/8 15:55
 * 你的指尖,拥有改变世界的力量
 * @description 小程序标签接口
 */
public interface TagService extends IService<Tag> {
    /**
     * 根据 id 列表获取标签名称列表
     *
     * @param ids id 列表(eg: 1,2,3)
     * @return List<String>
     */
    List<String> getTagNameListByIds(String ids);

    /**
     * 根据 type 获取标签列表
     *
     * @param type 标签类型
     * @return 标签列表
     */
    List<Tag> getTagListByType(String type);

    /**
     * 获取所有标签数据
     *
     * @return tags
     */
    List<Tag> getAllTags();
}
