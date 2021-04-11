package top.yzhelp.campus.model.other;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * @description 轮播图片
 */
@Data
@TableName("rotate_picture")
@ApiModel("轮播图")
public class RotatePicture implements Serializable {
  @TableId(type = IdType.AUTO)
  @ApiModelProperty("搜索ID,自增")
  private String id;
  @ApiModelProperty("类型")
  private String rotateType;
  @ApiModelProperty("图片链接")
  private String picUrl;
  @ApiModelProperty("跳转链接")
  private String toLink;
}