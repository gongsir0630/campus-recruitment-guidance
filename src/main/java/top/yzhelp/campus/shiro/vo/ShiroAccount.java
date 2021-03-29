package top.yzhelp.campus.shiro.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author <a href="https://github.com/gongsir0630">码之泪殇</a>
 * @date 2021/3/29 12:52
 * 你的指尖,拥有改变世界的力量
 * @description shiro 校验信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ShiroAccount implements Serializable {
  /**
   * 用户名
   */
  private String authName;
  /**
   * 校验密码
   */
  private String authSecret;
  /**
   * 角色
   */
  private List<String> role;
}
