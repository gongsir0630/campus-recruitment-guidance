package top.yzhelp.campus.controller.wx.me;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yzhelp.campus.controller.res.Result;
import top.yzhelp.campus.model.yh.School;
import top.yzhelp.campus.service.SchoolService;

import javax.annotation.Resource;

/**
 * @author <a href="https://github.com/gongsir0630">码之泪殇</a>
 * @date 2021/4/26 11:49
 * 你的指尖,拥有改变世界的力量
 * @description description
 */
@RestController
@Slf4j
@RequestMapping("wx/school")
@Api(tags = "MINIAPP-学校信息接口")
public class SchoolController {

  @Resource
  private SchoolService schoolService;

  @GetMapping("/list")
  @ApiOperation("获取可选学校列表")
  public ResponseEntity<Result<?>> getSchoolList() {
    return new ResponseEntity<>(Result.success(this.schoolService.getAllList()), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  @ApiOperation("根据id获取学校详情")
  @RequiresRoles("wx")
  public ResponseEntity<Result<?>> getSchoolById(@PathVariable int id) {
    School school = this.schoolService.getSchoolById(id);
    return new ResponseEntity<>(Result.success(school),HttpStatus.OK);
  }
}
