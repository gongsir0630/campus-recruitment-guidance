package top.yzhelp.campus.model.yzb;

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
 * @date 2021/3/29 12:40
 * 你的指尖,拥有改变世界的力量
 * @description 柚子帮成员信息
 */
@Data
@TableName("member")
@ApiModel("柚子帮成员信息")
public class Member implements Serializable {
  @TableId(type = IdType.AUTO)
  @ApiModelProperty("成员 ID,自增")
  private Integer id;
  @ApiModelProperty("用户 ID")
  private String openId;
  @ApiModelProperty("当前状态")
  private String currentState;
  @ApiModelProperty("头像")
  private String title;
  @ApiModelProperty("领域标签")
  private String fieldTags;
  @ApiModelProperty("成员介绍")
  private String introduction;
  @ApiModelProperty("擅长话题")
  private String topics;
  @ApiModelProperty("真实头像")
  private String avatar;
  @ApiModelProperty("认证状态")
  private String certificationStatus;
  @ApiModelProperty("申请时间")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date applyTime;
  @ApiModelProperty("点赞")
  private String likeList;
}
