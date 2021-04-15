package top.yzhelp.campus.controller.wx;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import top.yzhelp.campus.controller.res.CodeMsg;
import top.yzhelp.campus.controller.res.Result;
import top.yzhelp.campus.controller.wx.vo.MemberApplyResponse;
import top.yzhelp.campus.model.yh.EduInfo;
import top.yzhelp.campus.model.yh.JobInfo;
import top.yzhelp.campus.model.yh.WxUser;
import top.yzhelp.campus.model.yzb.Member;
import top.yzhelp.campus.service.*;
import top.yzhelp.campus.shiro.ShiroRealm;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="https://github.com/gongsir0630">码之泪殇</a>
 * @date 2021/4/14 16:32
 * 你的指尖,拥有改变世界的力量
 * @description 柚子帮成员模块接口
 */
@RestController
@Slf4j
@RequestMapping("wx/member")
@Api(tags = "小程序柚子帮接口")
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
  public ResponseEntity<Result<?>> like(@PathVariable int id) {
    this.memberService.likeById(id,this.getOpenId());
    return new ResponseEntity<>(Result.success(null),HttpStatus.OK);
  }

  @GetMapping("all")
  @ApiOperation("柚子帮列表展示")
  public ResponseEntity<Result<?>> all(@RequestParam(defaultValue = "1") int cur,
                                            @RequestParam(defaultValue = "10") int size) {
    IPage<Member> allMemberList = this.memberService.getAllMemberList(cur, size);
    List<Member> records = allMemberList.getRecords();
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
    IPage<MemberApplyResponse> newPage = new Page<>(allMemberList.getCurrent()
      ,allMemberList.getSize(),allMemberList.getTotal(),allMemberList.isSearchCount());
    newPage.setRecords(data);
    return new ResponseEntity<>(Result.success(newPage),HttpStatus.OK);
  }

  @PostMapping("apply")
  @ApiOperation("柚子帮成员申请")
  public ResponseEntity<Result<?>> apply(Member member) {
    member.setOpenId(getOpenId());
    Member newMember = this.memberService.saveOrUpdateMember(member);
    return new ResponseEntity<>(Result.success(newMember),HttpStatus.OK);
  }

  /**
   * 获取个人认证信息
   * @return data
   */
  @GetMapping("apply")
  @ApiOperation("获取个人认证信息")
  public ResponseEntity<Result<?>> getApply() {
    // 1. 获取用户个人信息
    WxUser userInfo = this.userService.getUserInfo(getOpenId());
    // 获取教育认证信息
    EduInfo edu = this.eduInfoService.getEduInfoById(userInfo.getEduId());
    if (edu == null || !Boolean.parseBoolean(edu.getStatus())) {
      return new ResponseEntity<>(Result.fail(CodeMsg.EDU_ERROR,null), HttpStatus.OK);
    }
    MemberApplyResponse response = new MemberApplyResponse(userInfo,edu);
    JobInfo job = this.jobInfoService.getJobInfoById(userInfo.getJobId());
    // 如果已经通过职位认证,优先显示职位信息
    if (job != null && Boolean.parseBoolean(job.getStatus())) {
      response.setSelectStatus("工作");
      // 设置所在公司
      response.setCompanyName(this.companyService.getCompanyById(job.getCompanyId()).getName());
    }
    // 2. 获取柚子帮成员信息
    Member detail = this.memberService.getMemberDetailByOpenId(getOpenId());
    response.setMember(detail);
    return new ResponseEntity<>(Result.success(response),HttpStatus.OK);
  }
}
