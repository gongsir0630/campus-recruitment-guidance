package top.yzhelp.campus.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import top.yzhelp.campus.mapper.NoticeMapper;
import top.yzhelp.campus.model.Notice;
import top.yzhelp.campus.service.NoticeService;

/**
 * @author <a href="https://github.com/gongsir0630">Kyle</a>
 * @date 2021/4/8 09:27
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
