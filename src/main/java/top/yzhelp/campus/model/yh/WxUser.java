package top.yzhelp.campus.model.yh;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author <a href="https://github.com/gongsir0630">码之泪殇</a>
 * @date 2021/3/29 12:40
 * 你的指尖,拥有改变世界的力量
 * @description 微信小程序用户信息
 */
@Data
@TableName("wx_user")
@ApiModel("小程序用户信息")
@EqualsAndHashCode
public class WxUser implements Serializable {
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
  @ApiModelProperty(value = "教育信息 ID",example = "1")
  private Integer eduId;
  @ApiModelProperty(value = "工作信息 ID",example = "1")
  private Integer jobId;
  @ApiModelProperty("个人简介")
  private String profile;

  @TableField(exist = false)
  private EduInfo eduInfo;
  @TableField(exist = false)
  private JobInfo jobInfo;
}
