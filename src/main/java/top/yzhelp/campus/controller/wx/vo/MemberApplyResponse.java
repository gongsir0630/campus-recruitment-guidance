package top.yzhelp.campus.controller.wx.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.yzhelp.campus.model.other.Tag;
import top.yzhelp.campus.model.yh.EduInfo;
import top.yzhelp.campus.model.yh.WxUser;
import top.yzhelp.campus.model.yzb.Member;

import java.io.Serializable;
import java.util.List;

/**
 * @author <a href="https://github.com/gongsir0630">码之泪殇</a>
 * @date 2021/4/14 16:44
 * 你的指尖,拥有改变世界的力量
 * @description description
 */
@Data
@ApiModel("柚子帮成员申请需要的信息")
@NoArgsConstructor
@AllArgsConstructor
public class MemberApplyResponse implements Serializable {
  @ApiModelProperty("真实姓名")
  private String realName;
  @ApiModelProperty("专业")
  private String major;
  @ApiModelProperty("年级")
  private String grade;
  @ApiModelProperty("可选状态")
  private String selectStatus;
  @ApiModelProperty("所在公司")
  private String companyName;
  @ApiModelProperty("柚子帮成员详情")
  private Member member;

  /**
   * 根据个人信息构造返回信息
   * @param user 用户基本信息
   * @param eduInfo 教育信息
   */
  public MemberApplyResponse(WxUser user, EduInfo eduInfo) {
    this.realName = user.getRealName();
    this.major = eduInfo.getMajor();
    this.grade = eduInfo.getEntrance();
    this.selectStatus = "学生";
    this.companyName = "请先完成职位认证";
  }
}
