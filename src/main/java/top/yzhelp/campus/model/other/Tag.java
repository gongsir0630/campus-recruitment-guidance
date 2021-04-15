package top.yzhelp.campus.model.other;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * @author <a href="https://github.com/gongsir0630">码之泪殇</a>
 * @date 2021/3/29 12:40
 * 你的指尖,拥有改变世界的力量
 * @description 标签 🏷
 */
@Data
@TableName("tag")
@ApiModel("标签信息")
public class Tag implements Serializable {
  @TableId(type = IdType.AUTO)
  @ApiModelProperty(value = "标签 Id,自增",example = "1")
  private Integer id;
  @ApiModelProperty("标签类型 ID")
  private String tagType;
  @ApiModelProperty("标签名称")
  private String tagName;
  /**
   * 标签类型: 0,1,2 分别代表 "话题标签","职位标签","领域标签"
   */
  public static final List<String> TAG_TYPE = Arrays.asList("话题标签","职位标签","领域标签");
}
