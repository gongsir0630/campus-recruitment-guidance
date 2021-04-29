package top.yzhelp.campus.controller.wx.vo;

import java.util.Arrays;
import java.util.List;

/**
 * @author <a href="https://github.com/gongsir0630">码之泪殇</a>
 * @date 2021/4/25 20:50
 * 你的指尖,拥有改变世界的力量
 * @description 常量
 */
public interface Constants {
  /**
   * 审核状态: 0,1,2 分别代表 "认证失败","待审核","认证通过"
   */
  public static final List<String> CET_STATUS = Arrays.asList("认证失败","待审核","认证通过");

  /**
   * 轮播图类型: 0,1,2,3 分别代表 "首页","内推-岗位订阅","内推-show","柚子帮入口"
   */
  public static final List<String> ROTATE_TYPE = Arrays.asList("首页","内推-岗位订阅","内推-show","柚子帮入口");

  /**
   * 标签类型: 0,1,2 分别代表 "话题标签","职位标签","领域标签"
   */
  public static final List<String> TAG_TYPE = Arrays.asList("话题标签","职位标签","领域标签");

  /**
   * 受理状态: 0,1 分别代表 "待受理","已受理"
   */
  public static final List<String> ACCEPT_STATUS = Arrays.asList("待受理","已受理");
}
