package top.yzhelp.campus.model.other;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

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
  @TableId(type = IdType.INPUT)
  @ApiModelProperty("用户 ID")
  private String openId;
  @ApiModelProperty("搜索关键字")
  private String keyword;
  @ApiModelProperty("职位订阅")
  private String subscribe;
  @TableField(exist = false)
  private Record record;

  @Data
  public static class Record {
    private String indexRec;
    private String ntRec;
    private String memberRec;
  }
}
