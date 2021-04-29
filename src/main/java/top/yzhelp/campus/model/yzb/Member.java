package top.yzhelp.campus.model.yzb;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
  @ApiModelProperty(value = "成员 ID,自增",example = "1")
  private Integer id;
  @ApiModelProperty("用户 ID")
  private String openId;
  @ApiModelProperty("当前状态:从可选状态选择")
  private String currentState;
  @ApiModelProperty("头衔:学生工作职务等")
  private String title;
  @ApiModelProperty("领域标签id,从领域标签选择,多个id以逗号分隔")
  private String fieldTags;
  @ApiModelProperty("成员介绍")
  private String introduction;
  @ApiModelProperty("擅长话题")
  private String topics;
  @ApiModelProperty("真实头像:先调用 upload 接口获取 url")
  private String photo;
  @ApiModelProperty("认证状态:需要审核")
  private String certificationStatus;
  @ApiModelProperty("申请时间")
  @JSONField(format="yyyy-MM-dd")
  @DateTimeFormat(pattern="yyyy-MM-dd")
  private Date applyTime;
  @ApiModelProperty("点赞")
  private String likeList;
  @ApiModelProperty(value = "点赞人数",example = "1")
  private Integer likeCount;
}
