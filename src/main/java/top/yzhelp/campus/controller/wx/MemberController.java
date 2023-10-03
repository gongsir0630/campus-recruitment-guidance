package top.yzhelp.campus.controller.wx;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.hutool.core.map.MapUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import top.yzhelp.campus.enums.CrgConstants;
import top.yzhelp.campus.model.common.Message;
import top.yzhelp.campus.model.user.Member;
import top.yzhelp.campus.service.MemberService;
import top.yzhelp.campus.shiro.ShiroRealm;

/**
 * @author <a href="https://github.com/gongsir0630">Kyle</a>
 * @date 2021/4/14 16:32
 * 你的指尖,拥有改变世界的力量
 * @description 柚子帮成员模块接口
 */
@RestController
@Slf4j
@RequestMapping("wx/member")
@Api(tags = "MINIAPP-小程序柚子帮接口")
public class MemberController {
    @Resource
    private MemberService memberService;

    /**
     * 从认证信息中获取用户 openId
     *
     * @return openId
     */
    private String getOpenId() {
        return ShiroRealm.getShiroAccount().getAuthName();
    }

    @GetMapping("/like/{id}")
    @ApiOperation("点赞 | 取消点赞")
    @RequiresRoles("wx")
    public ResponseEntity<Message<?>> like(@PathVariable int id) {
        this.memberService.likeById(id, this.getOpenId());
        return new ResponseEntity<>(Message.success(null), HttpStatus.OK);
    }

    @GetMapping("list")
    @ApiOperation("柚子帮列表展示")
    public ResponseEntity<Message<?>> all() {
        List<Member> records = this.memberService.getAllMemberList();
        Map<String, Object> res = MapUtil.newHashMap();
        res.put("list", records);
        res.put("total", records.size());
        return new ResponseEntity<>(Message.success(res), HttpStatus.OK);
    }

    @PostMapping("apply")
    @ApiOperation("柚子帮成员申请")
    @RequiresRoles("wx")
    public ResponseEntity<Message<?>> apply(Member member) {
        // 重复判断
        Member dbMember = this.memberService.getMemberDetailByOpenId(getOpenId());
        if (dbMember != null) {
            member.setId(dbMember.getId());
        }
        member.setOpenId(getOpenId());
        member.setCertificationStatus(CrgConstants.CET_STATUS.get(1));
        member.setApplyTime(new Date());
        Member newMember = this.memberService.saveOrUpdateMember(member);
        return new ResponseEntity<>(Message.success(newMember), HttpStatus.OK);
    }

    /**
     * 获取个人认证信息
     *
     * @return data
     */
    @GetMapping("apply")
    @ApiOperation("获取个人认证信息")
    @RequiresRoles("wx")
    public ResponseEntity<Message<?>> getApply() {
        // 获取柚子帮成员信息
        Member detail = this.memberService.getMemberDetailByOpenId(getOpenId());
        return new ResponseEntity<>(Message.success(detail), HttpStatus.OK);
    }
}
