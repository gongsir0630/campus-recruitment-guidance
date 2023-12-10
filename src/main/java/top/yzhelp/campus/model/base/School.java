package top.yzhelp.campus.model.base;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author <a href="https://github.com/gongsir0630">Kyle</a>
 * @date 2021/4/2 17:10
 * @description 学校信息
 */
@Data
@TableName("school")
@ApiModel("学校信息")
public class School implements Serializable {
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "学校 ID", example = "1")
    private Integer id;
    @ApiModelProperty("学校 Logo")
    private String logo;
    @ApiModelProperty("学校名称")
    private String name;
    @ApiModelProperty("专业列表")
    private String majorList;
    @ApiModelProperty("邮箱后缀")
    private String mailSuffix;
}
