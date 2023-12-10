package top.yzhelp.campus.controller.admin;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;

import cn.hutool.core.collection.ListUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import top.yzhelp.campus.controller.wx.MemberController;
import top.yzhelp.campus.enums.CrgConstants;
import top.yzhelp.campus.model.common.Message;
import top.yzhelp.campus.model.user.Member;
import top.yzhelp.campus.service.MemberService;

/**
 * @author <a href="https://github.com/gongsir0630">Kyle</a>
 * @date 2021/4/25 14:14
 * @description description
 */
@RestController
@Slf4j
@RequestMapping("admin/member")
@Api(tags = "ADMIN-柚子帮成员管理接口")
@RequiresRoles("admin")
public class MemberAdminController {

    @Resource
    private MemberService memberService;
    @Resource
    private MemberController memberController;

    @ApiOperation("申请审核")
    @PostMapping("/certificate/{status}")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "status", value = "审核状态: 0,1,2 分别代表 \"认证失败\",\"待审核\",\"认证通过\"")
    })
    public ResponseEntity<Message<?>> certificate(String ids,
        @PathVariable("status") int status) {
        List<String> idList = ListUtil.toList(ids.split(","));
        var member = new Member();
        member.setCertificationStatus(CrgConstants.CET_STATUS.get(status));
        this.memberService.update(member, new LambdaUpdateWrapper<Member>().in(Member::getId, idList));
        return new ResponseEntity<>(Message.success(null), HttpStatus.OK);
    }

    /**
     * 获取所有柚子帮成员
     *
     * @return 所有柚子帮成员列表
     */
    @GetMapping(path = "/list")
    @ApiOperation("获取所有柚子帮成员")
    @ApiResponses({
        @ApiResponse(code = 200, message = "接口调用成功")
    })
    public ResponseEntity<Message<?>> all() {
        return this.memberController.all();
    }

    @ApiOperation("更新信息")
    @PostMapping
    public ResponseEntity<Message<?>> update(Member info) {
        this.memberService.updateById(info);
        return new ResponseEntity<>(Message.success(null), HttpStatus.OK);
    }

    @ApiOperation("删除,多个 id 使用','分隔")
    @DeleteMapping
    public ResponseEntity<Message<?>> delete(String ids) {
        List<String> idList = ListUtil.toList(ids.split(","));
        this.memberService.removeByIds(idList);
        return new ResponseEntity<>(Message.success(null), HttpStatus.OK);
    }
}
