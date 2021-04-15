package top.yzhelp.campus.controller.wx.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.yzhelp.campus.model.dt.DynamicInfo;
import top.yzhelp.campus.model.yh.WxUser;

import java.io.Serializable;
import java.util.List;

/**
 * @author <a href="https://github.com/gongsir0630">码之泪殇</a>
 * @date 2021/4/13 14:32
 * 你的指尖,拥有改变世界的力量
 * @description 动态信息 API 封装
 */
@Data
@ApiModel("动态信息 API 封装")
@NoArgsConstructor
@AllArgsConstructor
public class DynamicResponse implements Serializable {
  @ApiModelProperty("头像")
  private String avatar;
  @ApiModelProperty("昵称")
  private String nickName;
  @ApiModelProperty("职位信息")
  private String jobTitle;
  @ApiModelProperty("专业信息")
  private String major;
  @ApiModelProperty("该动态的话题标签")
  private List<String> topTags;
  @ApiModelProperty("该动态的点赞数")
  private Integer likeCount;
  @ApiModelProperty("收藏状态")
  private Boolean collection;

  /**
   * 构造方法
   * @param user 用户信息
   * @param dynamicInfo 动态信息
   */
  public DynamicResponse(WxUser user, DynamicInfo dynamicInfo) {
    this.avatar = user.getAvatar();
    this.nickName = user.getNickName();
    this.likeCount = dynamicInfo.getLikeList().split(",").length;
    this.collection = dynamicInfo.getCollectionList().contains(user.getOpenId());
  }
}
