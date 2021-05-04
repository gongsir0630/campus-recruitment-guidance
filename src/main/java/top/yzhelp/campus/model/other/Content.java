package top.yzhelp.campus.model.other;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author <a href="https://github.com/gongsir0630">码之泪殇</a>
 * @date 2021/4/3 13:06
 * 你的指尖,拥有改变世界的力量
 * @description 内容管理信息
 */
@Data
@ApiModel("内容管理信息")
public class Content implements Serializable {
  @TableId(type = IdType.INPUT)
  @ApiModelProperty("用户 ID")
  private String openId;
  @ApiModelProperty("发布的动态 Ids")
  private List<Integer> publishNews;
  @ApiModelProperty("点赞动态 Ids")
  private List<Integer> likeNews;
  @ApiModelProperty("收藏动态 Ids")
  private List<Integer> collectNews;
  @ApiModelProperty("发布的内推 Ids")
  private List<Integer> publishRecommendations;
  @ApiModelProperty("点赞的内推 Ids")
  private List<Integer> likeRecommendations;
  @ApiModelProperty("关注我的 openIds")
  private List<String> followMe;
  @ApiModelProperty("我关注的 Ids")
  private List<Integer> myFollow;
}
