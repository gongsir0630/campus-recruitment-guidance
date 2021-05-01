package top.yzhelp.campus.controller.wx.help;

import cn.hutool.core.map.MapUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import top.yzhelp.campus.controller.res.Result;
import top.yzhelp.campus.controller.wx.vo.Constants;
import top.yzhelp.campus.controller.wx.vo.MemberApplyResponse;
import top.yzhelp.campus.model.yh.EduInfo;
import top.yzhelp.campus.model.yh.WxUser;
import top.yzhelp.campus.model.yzb.Member;
import top.yzhelp.campus.service.*;
import top.yzhelp.campus.shiro.ShiroRealm;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="https://github.com/gongsir0630">码之泪殇</a>
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
  private WxUserService userService;
  @Resource
  private EduInfoService eduInfoService;
  @Resource
  private JobInfoService jobInfoService;
  @Resource
  private CompanyService companyService;
  @Resource
  private MemberService memberService;

  /**
   * 从认证信息中获取用户 openId
   * @return openId
   */
  private String getOpenId() {
    return ShiroRealm.getShiroAccount().getAuthName();
  }

  @GetMapping("/like/{id}")
  @ApiOperation("点赞 | 取消点赞")
  @RequiresRoles("wx")
  public ResponseEntity<Result<?>> like(@PathVariable int id) {
    this.memberService.likeById(id,this.getOpenId());
    return new ResponseEntity<>(Result.success(null),HttpStatus.OK);
  }

  @GetMapping("list")
  @ApiOperation("柚子帮列表展示")
  public ResponseEntity<Result<?>> all() {
    List<Member> records = this.memberService.getAllMemberList();
    List<MemberApplyResponse> data = new ArrayList<>();
    records.forEach(member -> {
      final String openId = member.getOpenId();
      WxUser userInfo = this.userService.getUserInfo(openId);
      EduInfo edu = this.eduInfoService.getEduInfoById(userInfo.getEduId());
      MemberApplyResponse response = new MemberApplyResponse(userInfo,edu);
      response.setMember(member);
      response.setSelectStatus(member.getCurrentState());
      data.add(response);
    });
    Map<String,Object> res = MapUtil.newHashMap();
    res.put("list",data);
    res.put("total",data.size());
    return new ResponseEntity<>(Result.success(res),HttpStatus.OK);
  }

  @PostMapping("apply")
  @ApiOperation("柚子帮成员申请")
  @RequiresRoles("wx")
  public ResponseEntity<Result<?>> apply(Member member) {
    // 重复判断
    Member dbMember = this.memberService.getMemberDetailByOpenId(getOpenId());
    if (dbMember != null) {
      member.setId(dbMember.getId());
    }
    member.setOpenId(getOpenId());
    member.setCertificationStatus(Constants.CET_STATUS.get(1));
    Member newMember = this.memberService.saveOrUpdateMember(member);
    return new ResponseEntity<>(Result.success(newMember),HttpStatus.OK);
  }

  /**
   * 获取个人认证信息
   * @return data
   */
  @GetMapping("apply")
  @ApiOperation("获取个人认证信息")
  @RequiresRoles("wx")
  public ResponseEntity<Result<?>> getApply() {
    // 获取柚子帮成员信息
    Member detail = this.memberService.getMemberDetailByOpenId(getOpenId());
    return new ResponseEntity<>(Result.success(detail),HttpStatus.OK);
  }
}
