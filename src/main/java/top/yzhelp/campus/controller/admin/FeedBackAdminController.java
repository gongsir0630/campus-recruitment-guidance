package top.yzhelp.campus.controller.admin;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import top.yzhelp.campus.controller.res.Result;
import top.yzhelp.campus.model.other.FeedBack;
import top.yzhelp.campus.service.FeedBackService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="https://github.com/gongsir0630">码之泪殇</a>
 * @date 2021/4/25 14:14
 * 你的指尖,拥有改变世界的力量
 * @description description
 */
@RestController
@Slf4j
@RequestMapping("admin/feed")
@Api(tags = "ADMIN-用户反馈管理接口")
@RequiresRoles("admin")
public class FeedBackAdminController {

  @Resource
  private FeedBackService feedBackService;

  /**
   * 获取所有用户反馈
   * @return 所有用户反馈列表
   */
  @GetMapping(path = "/list")
  @ApiOperation("获取所有用户反馈")
  @ApiResponses({
    @ApiResponse(code = 200,message = "接口调用成功")
  })
  public ResponseEntity<Result<?>> all() {
    List<FeedBack> list = this.feedBackService.getAll();
    Map<String,Object> data = MapUtil.newHashMap();
    data.put("list",list);
    data.put("total",list.size());
    return new ResponseEntity<>(Result.success(data), HttpStatus.OK);
  }

  @ApiOperation("更新信息")
  @PostMapping
  public ResponseEntity<Result<?>> update(FeedBack feedBack) {
    this.feedBackService.updateById(feedBack);
    return new ResponseEntity<>(Result.success(null),HttpStatus.OK);
  }

  @ApiOperation("删除,多个 id 使用','分隔")
  @DeleteMapping
  public ResponseEntity<Result<?>> delete(String ids) {
    List<String> idList = ListUtil.toList(ids.split(","));
    this.feedBackService.removeByIds(idList);
    return new ResponseEntity<>(Result.success(null),HttpStatus.OK);
  }
}
