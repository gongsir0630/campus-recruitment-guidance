package top.yzhelp.campus.controller.wx;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.hutool.core.map.MapUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import top.yzhelp.campus.model.Notice;
import top.yzhelp.campus.model.common.Message;
import top.yzhelp.campus.service.NoticeService;

/**
 * @author <a href="https://github.com/gongsir0630">Kyle</a>
 * @date 2021/4/12 16:39
 * 你的指尖,拥有改变世界的力量
 * @description 小程序公告接口类
 */
@RestController
@Slf4j
@RequestMapping("wx/notice")
@Api(tags = "MINIAPP-小程序公告接口")
public class NoticeController {

    @Resource
    private NoticeService noticeService;

    /**
     * 小程序获取所有公告
     *
     * @return 所有公告列表
     */
    @GetMapping(path = "/all")
    @ApiOperation("小程序获取所有公告")
    //  @RequiresRoles("wx")
    public ResponseEntity<Message<?>> getAllNotices() {
        List<Notice> allNotices = this.noticeService.getAllNotices();
        Map<String, Object> res = MapUtil.newHashMap();
        res.put("list", allNotices);
        res.put("total", allNotices.size());
        return new ResponseEntity<>(Message.success(res), HttpStatus.OK);
    }

    /**
     * 小程序查看公告详情
     *
     * @param id 公告 id
     * @return 公告详情
     */
    @GetMapping("/{id}")
    @ApiOperation("小程序查看公告详情")
    public ResponseEntity<Message<?>> getNoticeDetail(@PathVariable("id") int id) {
        Notice notice = this.noticeService.getNoticeDetailById(id);
        return new ResponseEntity<>(Message.success(notice), HttpStatus.OK);
    }
}
