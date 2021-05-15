package top.yzhelp.campus.controller.admin;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import top.yzhelp.campus.controller.res.Result;
import top.yzhelp.campus.controller.wx.vo.Constants;
import top.yzhelp.campus.model.yh.EduInfo;
import top.yzhelp.campus.service.EduInfoService;
import top.yzhelp.campus.service.WxUserService;

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
@RequestMapping("admin/edu")
@Api(tags = "ADMIN-教育信息管理接口")
@RequiresRoles("admin")
public class EduInfoAdminController {

  @Resource
  private EduInfoService eduInfoService;
  @Resource
  private WxUserService userService;

  @ApiOperation("申请审核")
  @PostMapping("/certificate/{status}")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "status",value = "审核状态: 0,1,2 分别代表 \"认证失败\",\"待审核\",\"认证通过\"")
  })
  public ResponseEntity<Result<?>> certificate(String ids,
                                               @PathVariable("status") int status) {
    List<String> idList = ListUtil.toList(ids.split(","));
    var eduInfo = new EduInfo();
    eduInfo.setStatus(Constants.CET_STATUS.get(status));
    this.eduInfoService.update(eduInfo,new LambdaUpdateWrapper<EduInfo>().in(EduInfo::getId,idList));
    return new ResponseEntity<>(Result.success(null),HttpStatus.OK);
  }

  /**
   * 获取所有教育信息
   * @return 所有教育信息列表
   */
  @GetMapping(path = "/list")
  @ApiOperation("获取所有教育信息")
  @ApiResponses({
    @ApiResponse(code = 200,message = "接口调用成功")
  })
  public ResponseEntity<Result<?>> all() {
    List<EduInfo> allInfoList = this.eduInfoService.getAllInfoList();
    allInfoList.forEach(eduInfo -> eduInfo.setUser(this.userService.getUserInfo(eduInfo.getOpenId())));
    Map<String,Object> data = MapUtil.newHashMap();
    data.put("list",allInfoList);
    data.put("total",allInfoList.size());
    return new ResponseEntity<>(Result.success(data), HttpStatus.OK);
  }

  @ApiOperation("更新信息")
  @PostMapping
  public ResponseEntity<Result<?>> update(EduInfo eduInfo) {
    this.eduInfoService.updateById(eduInfo);
    return new ResponseEntity<>(Result.success(null),HttpStatus.OK);
  }

  @ApiOperation("删除,多个 id 使用','分隔")
  @DeleteMapping
  public ResponseEntity<Result<?>> delete(String ids) {
    List<String> idList = ListUtil.toList(ids.split(","));
    this.eduInfoService.removeByIds(idList);
    return new ResponseEntity<>(Result.success(null),HttpStatus.OK);
  }
}
