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
 * @description 企业信息
 */
@Data
@TableName("company")
@ApiModel("企业信息")
public class Company implements Serializable {
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "企业 ID", example = "1")
    private Integer id;
    @ApiModelProperty("企业 Logo")
    private String logo;
    @ApiModelProperty("企业名称")
    private String name;
    @ApiModelProperty("企业slogan")
    private String slogan;
    @ApiModelProperty("邮箱后缀")
    private String mailSuffix;
}
