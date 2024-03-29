package top.yzhelp.campus.model.user;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import top.yzhelp.campus.model.base.EduInfo;
import top.yzhelp.campus.model.base.JobInfo;

/**
 * @author <a href="https://github.com/gongsir0630">Kyle</a>
 * @date 2021/3/29 12:40
 * @description 微信小程序用户信息
 */
@Data
@TableName("wx_user")
@ApiModel("小程序用户信息")
@EqualsAndHashCode
public class CrgWxUser implements Serializable {
    @TableId(type = IdType.INPUT)
    @ApiModelProperty("用户 ID")
    private String openId;

    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("头像图片")
    private String avatar;

    @ApiModelProperty("性别")
    private String gender;

    @ApiModelProperty("真实姓名")
    private String realName;

    @ApiModelProperty("手机号码")
    private String phoneNumber;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty(value = "教育信息 ID", example = "1")
    private Integer eduId;

    @ApiModelProperty(value = "工作信息 ID", example = "1")
    private Integer jobId;

    @ApiModelProperty("个人简介")
    private String profile;

    @TableField(exist = false)
    private EduInfo eduInfo;
    @TableField(exist = false)
    private JobInfo jobInfo;
}
