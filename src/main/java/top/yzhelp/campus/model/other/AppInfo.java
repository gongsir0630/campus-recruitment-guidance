package top.yzhelp.campus.model.other;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author <a href="https://github.com/gongsir0630">码之泪殇</a>
 * @date 2021/3/29 12:40
 * 你的指尖,拥有改变世界的力量
 * @description 小程序信息 🏷
 */
@Data
@TableName("app_info")
@ApiModel("小程序信息")
public class AppInfo implements Serializable {
  @TableId(type = IdType.AUTO)
  @ApiModelProperty("主键 Id,自增")
  private Integer id;
  @ApiModelProperty("关于产品")
  private String about;
  @ApiModelProperty("加入我们")
  private String joinUs;
}
