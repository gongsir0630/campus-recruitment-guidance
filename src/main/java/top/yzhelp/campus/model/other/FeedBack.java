package top.yzhelp.campus.model.other;

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
 * @date 2021/4/3 13:06
 * 你的指尖,拥有改变世界的力量
 * @description 小程序反馈信息
 */
@Data
@TableName("feed_back")
@ApiModel("小程序反馈信息")
public class FeedBack implements Serializable {
  @TableId(type = IdType.AUTO)
  @ApiModelProperty("id,自增")
  private Integer id;
  @ApiModelProperty("用户 ID")
  private String openId;
  @ApiModelProperty("反馈类型")
  private String type;
  @ApiModelProperty("反馈内容")
  private String content;
  @ApiModelProperty("图片链接")
  private String picUrl;
  @ApiModelProperty("反馈时间")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date feedTime;
  @ApiModelProperty("受理状态")
  private String acceptStatus;
}
