package top.yzhelp.campus.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import top.yzhelp.campus.model.dt.Notice;

import java.util.List;

/**
 * @author <a href="https://github.com/gongsir0630">码之泪殇</a>
 * @date 2021/4/8 09:26
 * 你的指尖,拥有改变世界的力量
 * @description 小程序公告接口
 */
public interface NoticeService extends IService<Notice> {
  /**
   * 获取最新三条公告
   * @return 公告信息
   */
  List<Notice> getLatestNotices();

  /**
   * 获取所有公告信息, 支持分页
   * @param cur 当前页
   * @param size 每页数量
   * @return 公告信息
   */
  IPage<Notice> getAllNotices(long cur, long size);

  /**
   * 根据 id 查看公告详情
   * @param id 公告 id
   * @return 详情
   */
  Notice getNoticeDetailById(int id);
}
