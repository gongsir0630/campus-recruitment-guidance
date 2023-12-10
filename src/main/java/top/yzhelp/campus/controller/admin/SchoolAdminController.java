package top.yzhelp.campus.controller.admin;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.hutool.core.collection.ListUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import top.yzhelp.campus.controller.wx.SchoolController;
import top.yzhelp.campus.model.base.School;
import top.yzhelp.campus.model.common.Message;
import top.yzhelp.campus.service.SchoolService;

/**
 * @author <a href="https://github.com/gongsir0630">Kyle</a>
 * @date 2021/4/25 14:14
 * @description description
 */
@RestController
@Slf4j
@RequestMapping("admin/school")
@Api(tags = "ADMIN-学校信息管理接口")
@RequiresRoles("admin")
public class SchoolAdminController {

    @Resource
    private SchoolService schoolService;
    @Resource
    private SchoolController schoolController;

    @PutMapping
    public ResponseEntity<Message<?>> add(School school) {
        this.schoolService.save(school);
        return new ResponseEntity<>(Message.success(null), HttpStatus.OK);
    }

    /**
     * 获取所有学校信息
     *
     * @return 所有学校信息列表
     */
    @GetMapping(path = "/list")
    @ApiOperation("获取所有学校信息")
    @ApiResponses({
        @ApiResponse(code = 200, message = "接口调用成功")
    })
    public ResponseEntity<Message<?>> all() {
        return this.schoolController.getSchoolList();
    }

    @ApiOperation("更新信息")
    @PostMapping
    public ResponseEntity<Message<?>> update(School tag) {
        this.schoolService.updateById(tag);
        return new ResponseEntity<>(Message.success(null), HttpStatus.OK);
    }

    @ApiOperation("删除,多个 id 使用','分隔")
    @DeleteMapping
    public ResponseEntity<Message<?>> delete(String ids) {
        List<String> idList = ListUtil.toList(ids.split(","));
        this.schoolService.removeByIds(idList);
        return new ResponseEntity<>(Message.success(null), HttpStatus.OK);
    }
}
