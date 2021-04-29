package top.yzhelp.campus.controller.admin;

import cn.hutool.core.collection.ListUtil;
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
import top.yzhelp.campus.controller.wx.index.DynamicInfoController;
import top.yzhelp.campus.model.dt.DynamicInfo;
import top.yzhelp.campus.service.DynamicInfoService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author <a href="https://github.com/gongsir0630">码之泪殇</a>
 * @date 2021/4/25 14:14
 * 你的指尖,拥有改变世界的力量
 * @description description
 */
@RestController
@Slf4j
@RequestMapping("admin/dynamic")
@Api(tags = "ADMIN-动态管理接口")
@RequiresRoles("admin")
public class DtAdminController {

  @Resource
  private DynamicInfoService infoService;
  @Resource
  private DynamicInfoController infoController;

  /**
   * 获取所有动态
   * @return 所有动态列表
   */
  @GetMapping(path = "/list")
  @ApiOperation("获取所有动态")
  @ApiResponses({
    @ApiResponse(code = 200,message = "接口调用成功")
  })
  public ResponseEntity<Result<?>> all() {
    return this.infoController.getAllDynamicInfo();
  }

  @ApiOperation("更新信息")
  @PostMapping
  public ResponseEntity<Result<?>> update(DynamicInfo info) {
    this.infoService.updateById(info);
    return new ResponseEntity<>(Result.success(null),HttpStatus.OK);
  }

  @ApiOperation("删除,多个 id 使用','分隔")
  @DeleteMapping
  public ResponseEntity<Result<?>> delete(String ids) {
    List<String> idList = ListUtil.toList(ids.split(","));
    this.infoService.removeByIds(idList);
    return new ResponseEntity<>(Result.success(null),HttpStatus.OK);
  }
}
