package top.yzhelp.campus.model.project;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author <a href="https://github.com/gongsir0630">码之泪殇</a>
 * @date 2021/4/10 19:34
 * 你的指尖,拥有改变世界的力量
 * @description 秘钥信息: 七牛云, 阿里云秘钥信息
 */
@Data
@TableName("secret_key")
public class SecretKey {
  @TableId(type = IdType.AUTO)
  private Integer id;
  /**
   * 平台名称
   */
  private String platName;
  /**
   * ak
   */
  private String ak;
  /**
   * sk
   */
  private String sk;
}
