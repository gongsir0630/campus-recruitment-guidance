package top.yzhelp.campus.controller.admin;

import cn.hutool.core.collection.ListUtil;
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
import top.yzhelp.campus.model.other.RotatePicture;
import top.yzhelp.campus.service.RotatePictureService;

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
@RequestMapping("admin/pic")
@Api(tags = "ADMIN-轮播图管理接口")
@RequiresRoles("admin")
public class PictureAdminController {

  @Resource
  private RotatePictureService pictureService;

  /**
   * 获取所有轮播图
   * @return 所有轮播图列表
   */
  @GetMapping(path = "/list")
  @ApiOperation("获取所有轮播图")
  @ApiResponses({
    @ApiResponse(code = 200,message = "接口调用成功")
  })
  public ResponseEntity<Result<?>> all() {
    IPage<RotatePicture> allPics = this.pictureService.getAllPics();
    return new ResponseEntity<>(Result.success(allPics), HttpStatus.OK);
  }

  @ApiOperation("更新信息")
  @PostMapping
  public ResponseEntity<Result<?>> update(RotatePicture picture) {
    this.pictureService.updatePictureById(picture);
    return new ResponseEntity<>(Result.success(null),HttpStatus.OK);
  }

  @ApiOperation("删除,多个 id 使用','分隔")
  @DeleteMapping
  public ResponseEntity<Result<?>> delete(String ids) {
    List<String> idList = ListUtil.toList(ids.split(","));
    this.pictureService.removeByIds(idList);
    return new ResponseEntity<>(Result.success(null),HttpStatus.OK);
  }
}
