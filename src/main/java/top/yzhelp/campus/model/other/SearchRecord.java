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
 * @date 2021/3/29 12:40
 * 你的指尖,拥有改变世界的力量
 * @description 搜索记录
 */
@Data
@TableName("search_record")
@ApiModel("搜索记录")
public class SearchRecord implements Serializable {
  @TableId(type = IdType.AUTO)
  @ApiModelProperty(value = "搜索ID,自增",example = "1")
  private Integer id;
  @ApiModelProperty("用户 ID")
  private String openId;
  @ApiModelProperty("搜索类型")
  private String searchType;
  @ApiModelProperty("搜索关键字")
  private String keyword;
  @ApiModelProperty(value = "搜索次数",example = "1")
  private Integer searchTimes;
}
