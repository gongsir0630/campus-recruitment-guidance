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
import top.yzhelp.campus.model.Tag;
import top.yzhelp.campus.model.common.Message;
import top.yzhelp.campus.service.TagService;

/**
 * @author <a href="https://github.com/gongsir0630">Kyle</a>
 * @date 2021/4/25 14:14
 * @description description
 */
@RestController
@Slf4j
@RequestMapping("admin/tag")
@Api(tags = "ADMIN-标签管理接口")
@RequiresRoles("admin")
public class TagAdminController {

    @Resource
    private TagService tagService;

    @PutMapping
    public ResponseEntity<Message<?>> addTag(Tag tag) {
        this.tagService.save(tag);
        return new ResponseEntity<>(Message.success(null), HttpStatus.OK);
    }

    /**
     * 获取所有标签
     *
     * @return 所有标签列表
     */
    @GetMapping(path = "/list")
    @ApiOperation("获取所有标签")
    @ApiResponses({
        @ApiResponse(code = 200, message = "接口调用成功")
    })
    public ResponseEntity<Message<?>> all() {
        List<Tag> allTags = this.tagService.getAllTags();
        return new ResponseEntity<>(Message.success(allTags), HttpStatus.OK);
    }

    @ApiOperation("更新信息")
    @PostMapping
    public ResponseEntity<Message<?>> update(Tag tag) {
        this.tagService.updateById(tag);
        return new ResponseEntity<>(Message.success(null), HttpStatus.OK);
    }

    @ApiOperation("删除,多个 id 使用','分隔")
    @DeleteMapping
    public ResponseEntity<Message<?>> delete(String ids) {
        List<String> idList = ListUtil.toList(ids.split(","));
        this.tagService.removeByIds(idList);
        return new ResponseEntity<>(Message.success(null), HttpStatus.OK);
    }
}
