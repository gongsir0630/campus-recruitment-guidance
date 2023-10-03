package top.yzhelp.campus.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * @author <a href="https://github.com/gongsir0630">Kyle</a>
 * @date 2021/4/2 17:10
 * 你的指尖,拥有改变世界的力量
 * @description 首页动态信息
 */
@Data
@TableName("dynamic_info")
public class CrgDynamicInfo implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String openId;

    private String content;

    private String imgUrl;

    private String topicTags;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date publishTime;

    @Deprecated
    private String likeList;

    @Deprecated
    private String collectionList;
}
