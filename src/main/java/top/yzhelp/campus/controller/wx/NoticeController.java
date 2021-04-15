package top.yzhelp.campus.controller.wx;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import top.yzhelp.campus.controller.res.Result;
import top.yzhelp.campus.model.dt.Notice;
import top.yzhelp.campus.service.NoticeService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author <a href="https://github.com/gongsir0630">码之泪殇</a>
 * @date 2021/4/12 16:39
 * 你的指尖,拥有改变世界的力量
 * @description 小程序公告接口类
 */
@RestController
@Slf4j
@RequestMapping("wx/notice")
@Api(tags = "小程序公告接口")
public class NoticeController {

  @Resource
  private NoticeService noticeService;

  /**
   * 小程序获取所有公告,支持分页
   * @param cur 当前页
   * @param size 每页数量
   * @return 所有公告列表
   */
  @GetMapping(path = "/all")
  @ApiOperation("小程序获取所有公告,支持分页")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "cur",value = "当前页,默认1",required = false),
    @ApiImplicitParam(name = "size",value = "每页数量,默认5",required = false)
  })
  @ApiResponses({
    @ApiResponse(code = 200,message = "接口调用成功")
  })
  @RequiresRoles("wx")
  public ResponseEntity<Result<?>> getAllNotices(@RequestParam(value = "cur",defaultValue = "1") long cur,
                                                 @RequestParam(value = "size",defaultValue = "5") long size) {
    IPage<Notice> allNotices = this.noticeService.getAllNotices(cur, size);
    return new ResponseEntity<>(Result.success(allNotices), HttpStatus.OK);
  }

  /**
   * 小程序获取最新公告, 最新 3 条
   * @return 公告信息列表
   */
  @GetMapping("/Latest")
  @ApiOperation("小程序获取最新公告, 最新 3 条")
  private ResponseEntity<Result<?>> getLatestNotice() {
    List<Notice> latestNotices = this.noticeService.getLatestNotices();
    return new ResponseEntity<>(Result.success(latestNotices),HttpStatus.OK);
  }

  /**
   * 小程序查看公告详情
   * @param id 公告 id
   * @return 公告详情
   */
  @GetMapping("/{id}")
  @ApiOperation("小程序查看公告详情")
  public ResponseEntity<Result<?>> getNoticeDetail(@PathVariable("id") int id) {
    Notice notice = this.noticeService.getNoticeDetailById(id);
    return new ResponseEntity<>(Result.success(notice),HttpStatus.OK);
  }
}
