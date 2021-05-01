package top.yzhelp.campus.controller.wx.me;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yzhelp.campus.controller.res.Result;
import top.yzhelp.campus.service.JobInfoService;

import javax.annotation.Resource;

/**
 * @author <a href="https://github.com/gongsir0630">码之泪殇</a>
 * @date 2021/4/26 11:49
 * 你的指尖,拥有改变世界的力量
 * @description description
 */
@RestController
@Slf4j
@RequestMapping("wx/job")
@Api(tags = "MINIAPP-工作信息接口")
public class JobInfoController {

  @Resource
  private JobInfoService jobInfoService;

  @GetMapping("/{id}")
  @RequiresRoles("wx")
  public ResponseEntity<Result<?>> getEduInfoById(@PathVariable int id) {
    return new ResponseEntity<>(Result.success(this.jobInfoService.getJobInfoById(id)), HttpStatus.OK);
  }
}
