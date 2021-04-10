package top.yzhelp.campus.model.nt;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author <a href="https://github.com/gongsir0630">码之泪殇</a>
 * @date 2021/4/2 17:10
 * 你的指尖,拥有改变世界的力量
 * @description 内推信息
 */
@Data
@TableName("recommendation")
@ApiModel("内推信息")
public class Recommendation implements Serializable {
  @TableId(type = IdType.AUTO)
  @ApiModelProperty("内推 ID")
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
}
