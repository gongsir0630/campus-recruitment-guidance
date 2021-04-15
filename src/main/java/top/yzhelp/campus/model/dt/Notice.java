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
 * @description 公告信息
 */
@Data
@TableName("notice")
@ApiModel("公告信息")
public class Notice implements Serializable {
  @TableId(type = IdType.AUTO)
  @ApiModelProperty(value = "公告 ID",example = "1")
  private Integer id;
  @ApiModelProperty("公告标题")
  private String title;
  @ApiModelProperty("公告内容")
  private String content;
  @ApiModelProperty("发布时间")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date publishTime;
}
