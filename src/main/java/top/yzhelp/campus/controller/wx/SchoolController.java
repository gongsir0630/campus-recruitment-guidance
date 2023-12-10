package top.yzhelp.campus.controller.wx;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import top.yzhelp.campus.model.base.School;
import top.yzhelp.campus.model.common.Message;
import top.yzhelp.campus.service.SchoolService;

/**
 * @author <a href="https://github.com/gongsir0630">Kyle</a>
 * @date 2021/4/26 11:49
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
    public ResponseEntity<Message<?>> getSchoolList() {
        return new ResponseEntity<>(Message.success(this.schoolService.getAllList()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ApiOperation("根据id获取学校详情")
    @RequiresRoles("wx")
    public ResponseEntity<Message<?>> getSchoolById(@PathVariable int id) {
        School school = this.schoolService.getSchoolById(id);
        return new ResponseEntity<>(Message.success(school), HttpStatus.OK);
    }
}
