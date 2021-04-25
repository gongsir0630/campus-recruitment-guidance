package top.yzhelp.campus.controller.wx.index;

import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import top.yzhelp.campus.controller.res.Result;
import top.yzhelp.campus.controller.wx.vo.DynamicResponse;
import top.yzhelp.campus.model.dt.DynamicInfo;
import top.yzhelp.campus.model.yh.EduInfo;
import top.yzhelp.campus.model.yh.JobInfo;
import top.yzhelp.campus.model.yh.WxUser;
import top.yzhelp.campus.service.*;
import top.yzhelp.campus.shiro.ShiroRealm;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author <a href="https://github.com/gongsir0630">码之泪殇</a>
 * @date 2021/4/13 13:36
 * 你的指尖,拥有改变世界的力量
 * @description 小程序动态信息
 */
@RestController
@Slf4j
@RequestMapping("wx/dynamic")
@Api(tags = "小程序动态信息")
public class DynamicInfoController {

  @Resource
  private DynamicInfoService dynamicInfoService;
  @Resource
  private WxUserService userService;
  @Resource
  private JobInfoService jobInfoService;
  @Resource
  private EduInfoService eduInfoService;
  @Resource
  private TagService tagService;

  /**
   * 从认证信息中获取用户 openId
   * @return openId
   */
  private String getOpenId() {
    return ShiroRealm.getShiroAccount().getAuthName();
  }

  @PostMapping
  @ApiOperation("发布动态 | 更新动态")
  public ResponseEntity<Result<?>> saveOrUpdate(DynamicInfo info) {
    DynamicInfo dynamicInfo = this.dynamicInfoService.saveOrUpdateDt(info);
    DynamicResponse response = new DynamicResponse(this.userService.getUserInfo(getOpenId()),dynamicInfo);
    return new ResponseEntity<>(Result.success(response),HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  @ApiOperation("删除动态")
  public ResponseEntity<Result<?>> deleteById(@PathVariable int id) {
    this.dynamicInfoService.deleteDtById(id);
    return new ResponseEntity<>(Result.success(null),HttpStatus.OK);
  }

  @GetMapping("/collection/{id}")
  @ApiOperation("收藏 | 取消收藏")
  public ResponseEntity<Result<?>> collection(@PathVariable int id) {
    this.dynamicInfoService.collection(id,this.getOpenId());
    return new ResponseEntity<>(Result.success(null),HttpStatus.OK);
  }

  /**
   * 点赞动态
   * @param id 动态 id
   * @return data
   */
  @GetMapping("/like/{id}")
  @ApiOperation("点赞 | 取消点赞")
  public ResponseEntity<Result<?>> like(@PathVariable int id) {
    this.dynamicInfoService.likeById(id,this.getOpenId());
    return new ResponseEntity<>(Result.success(null),HttpStatus.OK);
  }

  /**
   * 小程序首页动态显示
   * @param cur 当前页
   * @param size 每页数量
   * @return data
   */
  @GetMapping("/all")
  @ApiOperation("动态列表")
  public ResponseEntity<Result<?>> getAllDynamicInfo(@RequestParam(defaultValue = "1") int cur,
                                                     @RequestParam(defaultValue = "10") int size) {
    IPage<DynamicInfo> allDtInfoList = this.dynamicInfoService.getAllDtInfoList(cur, size);
    List<DynamicInfo> records = allDtInfoList.getRecords();
    List<DynamicResponse> data = new ArrayList<>();
    // 对信息进行进一步处理
    records.forEach(dt -> {
      // todo: 获取发布人信息
      WxUser userInfo = this.userService.getUserInfo(dt.getOpenId());
      // 转换话题标签列表
      List<String> tags = tagService.getTagNameListByIds(dt.getTopicTags());
      dt.setTopicTags(String.join(",",tags));
      // 转换点赞列表: id -> nickName
      List<String> openIds = ListUtil.toList(dt.getLikeList().split(","));
      LinkedList<String> likeList2NameList = new LinkedList<>();
      openIds.forEach(openId -> {
        likeList2NameList.add(this.userService.getUserInfo(openId).getNickName());
      });
      // 如果当前用户已经点赞,则将该用户移至列表开头
      if (openIds.contains(getOpenId())) {
        likeList2NameList.removeIf(id->id.equals(getOpenId()));
        likeList2NameList.addFirst(this.userService.getUserInfo(getOpenId()).getNickName());
      }
      dt.setLikeList(String.join(",",likeList2NameList));
      DynamicResponse response = new DynamicResponse(userInfo, dt);
      // todo: 设置工作认证信息
      JobInfo job = jobInfoService.getJobInfoByOpenId(userInfo.getOpenId());
      if (job == null || !Boolean.parseBoolean(job.getStatus())) {
        response.setJobTitle("未认证职业信息");
      } else {
        response.setJobTitle(job.getJobTitle());
      }
      // todo: 设置工作认证信息
      EduInfo edu = eduInfoService.getEduInfoByOpenId(userInfo.getOpenId());
      if (edu == null || !Boolean.parseBoolean(edu.getStatus())) {
        response.setMajor("未认证教育信息");
      } else {
        response.setMajor(response.getMajor());
      }
      data.add(response);
    });
    // 重新进行分页封装
    IPage<DynamicResponse> newPage = new Page<>(allDtInfoList.getCurrent()
      ,allDtInfoList.getSize()
      ,allDtInfoList.getTotal()
      ,allDtInfoList.isSearchCount());
    newPage.setRecords(data);
    return new ResponseEntity<>(Result.success(newPage), HttpStatus.OK);
  }
}
