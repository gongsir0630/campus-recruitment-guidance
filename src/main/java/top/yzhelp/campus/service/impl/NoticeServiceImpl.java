package top.yzhelp.campus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.yzhelp.campus.mapper.NoticeMapper;
import top.yzhelp.campus.model.dt.Notice;
import top.yzhelp.campus.service.NoticeService;

import java.util.List;

/**
 * @author <a href="https://github.com/gongsir0630">码之泪殇</a>
 * @date 2021/4/8 09:27
 * 你的指尖,拥有改变世界的力量
 * @description 小程序公告接口实现
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {
  /**
   * 获取所有公告信息, 支持分页
   *
   * @return 公告信息
   */
  @Override
  public List<Notice> getAllNotices() {
    return this.list(new LambdaQueryWrapper<Notice>().orderByDesc(Notice::getId));
  }

  /**
   * 根据 id 查看公告详情
   *
   * @param id 公告 id
   * @return 详情
   */
  @Override
  public Notice getNoticeDetailById(int id) {
    return this.getById(id);
  }

  /**
   * 更新公告信息
   *
   * @param notice 公告信息
   */
  @Override
  public void updateNoticeById(Notice notice) {
    this.updateById(notice);
  }
}
