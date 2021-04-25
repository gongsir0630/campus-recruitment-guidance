package top.yzhelp.campus.model.admin;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author <a href="https://github.com/gongsir0630">码之泪殇</a>
 * @date 2021/4/8 16:03
 * 你的指尖,拥有改变世界的力量
 * @description 后台管理员用户信息
 */
@Data
@TableName("admin_user")
@ApiModel("管理员用户信息")
public class AdminUser implements Serializable {
  @TableId(type = IdType.INPUT)
  @ApiModelProperty("用户账号 ID")
  private String userId;
  @ApiModelProperty("用户密码")
  @JsonIgnore
  private String password;
  @ApiModelProperty("昵称")
  private String nickName;
  @ApiModelProperty("头像图片")
  private String avatar;
  @ApiModelProperty("手机号码")
  private String phoneNumber;
  @ApiModelProperty("邮箱")
  private String email;
}
