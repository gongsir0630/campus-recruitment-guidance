package top.yzhelp.campus.controller.admin;

import cn.hutool.core.collection.ListUtil;
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
import top.yzhelp.campus.controller.wx.me.CompanyController;
import top.yzhelp.campus.model.yh.Company;
import top.yzhelp.campus.service.CompanyService;

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
@RequestMapping("admin/company")
@Api(tags = "ADMIN-公司信息管理接口")
@RequiresRoles("admin")
public class CompanyAdminController {

  @Resource
  private CompanyService companyService;
  @Resource
  private CompanyController companyController;

  @PutMapping
  @ApiOperation("添加公司信息")
  public ResponseEntity<Result<?>> add(Company company) {
    this.companyService.save(company);
    return new ResponseEntity<>(Result.success(null), HttpStatus.OK);
  }

  /**
   * 获取所有公司信息
   * @return 所有公司信息列表
   */
  @GetMapping(path = "/list")
  @ApiOperation("获取所有公司信息")
  @ApiResponses({
    @ApiResponse(code = 200,message = "接口调用成功")
  })
  public ResponseEntity<Result<?>> all() {
    return this.companyController.list();
  }

  @ApiOperation("更新信息")
  @PostMapping
  public ResponseEntity<Result<?>> update(Company company) {
    this.companyService.updateById(company);
    return new ResponseEntity<>(Result.success(null),HttpStatus.OK);
  }

  @ApiOperation("删除,多个 id 使用','分隔")
  @DeleteMapping
  public ResponseEntity<Result<?>> delete(String ids) {
    List<String> idList = ListUtil.toList(ids.split(","));
    this.companyService.removeByIds(idList);
    return new ResponseEntity<>(Result.success(null),HttpStatus.OK);
  }
}