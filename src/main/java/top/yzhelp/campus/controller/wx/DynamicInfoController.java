package top.yzhelp.campus.controller.wx;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

import cn.hutool.core.map.MapUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import top.yzhelp.campus.enums.CrgConstants;
import top.yzhelp.campus.model.CrgDynamicInfo;
import top.yzhelp.campus.model.base.EduInfo;
import top.yzhelp.campus.model.base.JobInfo;
import top.yzhelp.campus.model.common.Message;
import top.yzhelp.campus.model.user.CrgWxUser;
import top.yzhelp.campus.service.DynamicInfoService;
import top.yzhelp.campus.service.EduInfoService;
import top.yzhelp.campus.service.JobInfoService;
import top.yzhelp.campus.service.TagService;
import top.yzhelp.campus.service.WxUserService;
import top.yzhelp.campus.shiro.ShiroRealm;
import top.yzhelp.campus.vo.DynamicInfoVO;

/**
 * @author <a href="https://github.com/gongsir0630">Kyle</a>
 * @date 2021/4/13 13:36
 * 你的指尖,拥有改变世界的力量
 * @description 小程序动态信息
 */
@RestController
@Slf4j
@RequestMapping("wx/dynamic")
@Api(tags = "MINIAPP-小程序动态信息")
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
     *
     * @return openId
     */
    private String getOpenId() {
        return ShiroRealm.getShiroAccount().getAuthName();
    }

    @PostMapping
    @ApiOperation("发布动态 | 更新动态")
    @RequiresRoles("wx")
    public ResponseEntity<Message<?>> saveOrUpdate(CrgDynamicInfo info) {
        info.setOpenId(this.getOpenId());
        info.setPublishTime(System.currentTimeMillis());
        CrgDynamicInfo dynamicInfo = this.dynamicInfoService.saveOrUpdateDt(info);
        DynamicInfoVO response = new DynamicInfoVO(this.userService.getUserInfo(getOpenId()), dynamicInfo);
        return new ResponseEntity<>(Message.success(response), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除动态")
    @RequiresRoles("wx")
    public ResponseEntity<Message<?>> deleteById(@PathVariable int id) {
        this.dynamicInfoService.deleteDtById(id);
        return new ResponseEntity<>(Message.success(null), HttpStatus.OK);
    }

    @GetMapping("/collection/{id}")
    @ApiOperation("收藏 | 取消收藏")
    @RequiresRoles("wx")
    public ResponseEntity<Message<?>> collection(@PathVariable int id) {
        this.dynamicInfoService.collection(id, this.getOpenId());
        return new ResponseEntity<>(Message.success(null), HttpStatus.OK);
    }

    /**
     * 点赞动态
     *
     * @param id 动态 id
     * @return data
     */
    @GetMapping("/like/{id}")
    @ApiOperation("点赞 | 取消点赞")
    @RequiresRoles("wx")
    public ResponseEntity<Message<?>> like(@PathVariable int id) {
        this.dynamicInfoService.likeById(id, this.getOpenId());
        return new ResponseEntity<>(Message.success(null), HttpStatus.OK);
    }

    /**
     * 小程序首页动态显示
     *
     * @return data
     */
    @GetMapping("/all")
    @ApiOperation("动态列表")
    public ResponseEntity<Message<?>> getAllDynamicInfo() {
        List<CrgDynamicInfo> records = this.dynamicInfoService.getAllDtInfoList();
        List<DynamicInfoVO> data = new ArrayList<>();
        // 对信息进行进一步处理
        records.forEach(dt -> {
            // 获取发布人信息
            CrgWxUser userInfo = this.userService.getUserInfo(dt.getOpenId());
            // 转换话题标签列表
            List<String> tags = dt.getTopicTags() != null
                                ? tagService.getTagNameListByIds(dt.getTopicTags())
                                : new ArrayList<>();
            dt.setTopicTags(String.join(",", tags));
            DynamicInfoVO response = new DynamicInfoVO(userInfo, dt);
            response.setUser(userInfo);
            // 设置工作认证信息
            JobInfo job = jobInfoService.getJobInfoByOpenId(userInfo.getOpenId());
            if (job == null || !CrgConstants.CET_STATUS.get(2).equals(job.getStatus())) {
                response.setJobTitle("未认证职业信息");
            } else {
                response.setJobTitle(job.getJobTitle());
            }
            // 设置工作认证信息
            EduInfo edu = eduInfoService.getEduInfoByOpenId(userInfo.getOpenId());
            if (edu == null || !CrgConstants.CET_STATUS.get(2).equals(edu.getStatus())) {
                response.setMajor("未认证教育信息");
            } else {
                response.setMajor(edu.getMajor());
            }
            response.setIsLike(dt.getLikeList() == null ? Boolean.FALSE : dt.getLikeList().contains(getOpenId()));
            response.setCollection(
                dt.getCollectionList() == null ? Boolean.FALSE : dt.getCollectionList().contains(getOpenId()));
            data.add(response);
        });
        Map<String, Object> res = MapUtil.newHashMap();
        res.put("list", data);
        res.put("total", data.size());
        return new ResponseEntity<>(Message.success(res), HttpStatus.OK);
    }
}
