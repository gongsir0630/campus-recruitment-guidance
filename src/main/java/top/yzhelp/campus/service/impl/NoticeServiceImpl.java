package top.yzhelp.campus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.yzhelp.campus.mapper.NoticeMapper;
import top.yzhelp.campus.model.dt.Notice;
import top.yzhelp.campus.service.NoticeService;

/**
 * @author <a href="https://github.com/gongsir0630">码之泪殇</a>
 * @date 2021/4/8 09:27
 * 你的指尖,拥有改变世界的力量
 * @description 小程序公告接口实现
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {
}
