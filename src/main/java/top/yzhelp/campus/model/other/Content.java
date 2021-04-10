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
 * @date 2021/4/3 13:06
 * 你的指尖,拥有改变世界的力量
 * @description 内容管理信息
 */
@Data
@TableName("content")
@ApiModel("内容管理信息")
public class Content implements Serializable {
  @TableId(type = IdType.AUTO)
  @ApiModelProperty("id,自增")
  private Integer id;
  @ApiModelProperty("用户 ID")
  private String openId;
  @ApiModelProperty("发布的动态 Ids")
  private String publishNews;
  @ApiModelProperty("点赞动态 Ids")
  private String likeNews;
  @ApiModelProperty("收藏动态 Ids")
  private String collectNews;
  @ApiModelProperty("发布的内推 Ids")
  private String publishRecommendations;
  @ApiModelProperty("点赞的内推 Ids")
  private String likeRecommendations;
  @ApiModelProperty("点赞成员 Ids")
  private String likeMembers;
}
