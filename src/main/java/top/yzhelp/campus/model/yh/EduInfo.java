package top.yzhelp.campus.model.yh;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author <a href="https://github.com/gongsir0630">码之泪殇</a>
 * @date 2021/4/2 17:10
 * 你的指尖,拥有改变世界的力量
 * @description 教育信息
 */
@Data
@TableName("edu_info")
@ApiModel("教育信息")
public class EduInfo implements Serializable {
  @TableId(type = IdType.AUTO)
  @ApiModelProperty("教育信息 ID")
  private Integer id;
  @ApiModelProperty("用户 openId")
  private String openId;
  @ApiModelProperty("学校 ID")
  private Integer schoolId;
  @ApiModelProperty("专业名称")
  private String major;
  @ApiModelProperty("入学年份")
  private String entrance;
  @ApiModelProperty("毕业年份")
  private String graduate;
  @ApiModelProperty("学历等级")
  private String level;
  @ApiModelProperty("经历描述")
  private String description;
  @ApiModelProperty("认证状态")
  private String status;
}
