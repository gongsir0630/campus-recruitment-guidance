package top.yzhelp.campus.model.dt;

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
 * @description 首页动态信息
 */
@Data
@TableName("dynamic_info")
@ApiModel("首页动态信息")
public class DynamicInfo implements Serializable {
  @TableId(type = IdType.AUTO)
  @ApiModelProperty(value = "动态 ID",example = "1")
  private Integer id;
  @ApiModelProperty("用户 ID")
  private String openId;
  @ApiModelProperty("动态内容")
  private String content;
  @ApiModelProperty("图片链接")
  private String imgUrl;
  @ApiModelProperty("话题标签 id 列表,多个标签以','分隔")
  private String topicTags;
  @ApiModelProperty("发布时间")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date publishTime;
  @ApiModelProperty("点赞")
  private String likeList;
  @ApiModelProperty("收藏")
  private String collectionList;
}
