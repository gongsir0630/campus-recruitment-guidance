package top.yzhelp.campus.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import top.yzhelp.campus.model.user.CrgWxUser;

/**
 * @author <a href="https://github.com/gongsir0630">Kyle</a>
 * @date 2021/4/2 17:10
 * @description 内推信息
 */
@Data
@TableName("recommendation")
@ApiModel("内推信息")
public class Recommendation implements Serializable {
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "内推 ID", example = "1")
    private Integer id;
    @ApiModelProperty("用户 ID")
    private String openId;
    @ApiModelProperty("内推形式")
    private String form;
    @ApiModelProperty("内推详情")
    private String details;
    @ApiModelProperty("图片链接")
    private String imgUrl;
    @ApiModelProperty("职位标签")
    private String positionTags;
    @ApiModelProperty("发布时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date publishTime;
    @ApiModelProperty("点赞")
    private String likeList;
    @TableField(exist = false)
    private CrgWxUser user;
}
