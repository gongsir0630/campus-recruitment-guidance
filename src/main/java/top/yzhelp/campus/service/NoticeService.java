package top.yzhelp.campus.service;

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
   * 获取所有公告信息
   * @return 公告信息
   */
  List<Notice> getAllNotices();

  /**
   * 根据 id 查看公告详情
   * @param id 公告 id
   * @return 详情
   */
  Notice getNoticeDetailById(int id);

  /**
   * 更新公告信息
   * @param notice 公告信息
   */
  void updateNoticeById(Notice notice);
}
