package top.yzhelp.campus.controller.wx.me;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yzhelp.campus.controller.res.Result;
import top.yzhelp.campus.model.yh.Company;
import top.yzhelp.campus.service.CompanyService;

import javax.annotation.Resource;

/**
 * @author <a href="https://github.com/gongsir0630">码之泪殇</a>
 * @date 2021/4/26 11:49
 * 你的指尖,拥有改变世界的力量
 * @description description
 */
@RestController
@Slf4j
@RequestMapping("wx/company")
@Api(tags = "MINIAPP-公司信息接口")
public class CompanyController {

  @Resource
  private CompanyService companyService;

  @GetMapping("/list")
  @ApiOperation("获取可选公司列表")
  public ResponseEntity<Result<?>> list() {
    return new ResponseEntity<>(Result.success(this.companyService.getAllList()), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  @ApiOperation("根据id获取公司详情")
  public ResponseEntity<Result<?>> getCompanyById(@PathVariable int id) {
    Company company = this.companyService.getCompanyById(id);
    return new ResponseEntity<>(Result.success(company),HttpStatus.OK);
  }
}
