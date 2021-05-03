package top.yzhelp.campus.controller.wx.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import top.yzhelp.campus.model.nt.Recommendation;
import top.yzhelp.campus.model.yh.*;

import java.io.Serializable;

/**
 * @author <a href="https://github.com/gongsir0630">码之泪殇</a>
 * @date 2021/4/13 14:32
 * 你的指尖,拥有改变世界的力量
 * @description 内推信息 API 封装
 */
@EqualsAndHashCode(callSuper = true)
@ApiModel("内推信息 API 封装")
@Data
public class RecommendationResponse extends Recommendation implements Serializable {
  private WxUser user;
  private EduInfo eduInfo;
  private JobInfo jobInfo;
  private School school;
  private Company company;

  public RecommendationResponse(Recommendation recommendation) {
    this.setId(recommendation.getId());
    this.setOpenId(recommendation.getOpenId());
    this.setForm(recommendation.getForm());
    this.setDetails(recommendation.getDetails());
    this.setImgUrl(recommendation.getImgUrl());
    this.setPublishTime(recommendation.getPublishTime());
    this.setLikeList(recommendation.getLikeList());
  }
}
