package top.yzhelp.campus.model.base;

import java.io.Serializable;

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
 * 你的指尖,拥有改变世界的力量
 * @description 工作信息
 */
@Data
@TableName("job_info")
@ApiModel("工作信息")
public class JobInfo implements Serializable {
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "工作信息 ID", example = "1")
    private Integer id;
    @ApiModelProperty("用户 openId")
    private String openId;
    @ApiModelProperty(value = "企业 ID", example = "1")
    private Integer companyId;
    @ApiModelProperty("部门名称")
    private String department;
    @ApiModelProperty("职位名称")
    private String jobTitle;
    @ApiModelProperty("经历描述")
    private String description;
    @ApiModelProperty("认证状态")
    private String status;
    @TableField(exist = false)
    private Company company;
    @TableField(exist = false)
    private CrgWxUser user;
}
