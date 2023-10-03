package top.yzhelp.campus.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import top.yzhelp.campus.enums.CrgConstants;
import top.yzhelp.campus.enums.WebResultCode;
import top.yzhelp.campus.exception.AppRuntimeException;
import top.yzhelp.campus.model.RotatePicture;
import top.yzhelp.campus.model.Tag;
import top.yzhelp.campus.model.common.Message;
import top.yzhelp.campus.service.RotatePictureService;
import top.yzhelp.campus.service.TagService;
import top.yzhelp.campus.util.QiNiuUtil;

/**
 * @author <a href="https://github.com/gongsir0630">Kyle</a>
 * @date 2021/4/10 22:32
 * 你的指尖,拥有改变世界的力量
 * @description 公开接口
 */
@RestController
@Slf4j
@RequestMapping("public")
@Api(tags = "PUBLIC-公开接口,无需token认证")
public class PublicController extends AbstractCrgController {

    @Value("${spring.application.name}")
    private String appName;

    @Resource
    private RotatePictureService pictureService;
    @Resource
    private TagService tagService;

    @GetMapping("/tags/{type}")
    @ApiOperation("根据标签类型获取标签列表")
    public ResponseEntity<Message<?>> tagList(@PathVariable Integer type) {
        type = type == null ? 0 : type;
        List<Tag> tags = this.tagService.getTagListByType(CrgConstants.TAG_TYPE.get(type));
        return new ResponseEntity<>(Message.success(tags), HttpStatus.OK);
    }

    /**
     * 轮播图接口
     *
     * @param type 轮播图类型
     * @return 轮播图列表
     */
    @GetMapping(path = "/banner/{type}")
    @ApiOperation("小程序获取轮播图")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "type", value = "轮播图类型: 0,1,2,3 分别代表 \"首页\",\"内推-岗位订阅\",\"内推-show\",\"柚子帮入口\"",
            required = true),
    })
    @ApiResponses({
        @ApiResponse(code = 200, message = "接口调用成功")
    })
    public ResponseEntity<Message<?>> getBanners(@PathVariable("type") String type) {
        type =
            !StrUtil.isBlank(type) ? CrgConstants.ROTATE_TYPE.get(Integer.parseInt(type)) : CrgConstants.ROTATE_TYPE.get(0);
        List<RotatePicture> banners = this.pictureService.getBannersByMiniApp(type);
        return new ResponseEntity<>(Message.success(banners), HttpStatus.OK);
    }

    /**
     * 图片上传通用接口
     *
     * @param file 图片二进制文件
     * @param key 文件唯一 key, 默认服务端自动生成,也可以前端自定义
     * @return 图片 url 地址
     */
    @PostMapping(path = "/upload")
    @ApiOperation("图片上传")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "file", value = "图片文件", required = true),
        @ApiImplicitParam(name = "key", value = "文件唯一 key, 默认服务端自动生成,也可以前端自定义", required = false)
    })
    @ApiResponses({
        @ApiResponse(code = 200, message = "接口调用成功")
    })
    public ResponseEntity<Message<Map<String, Object>>> uploadFile(MultipartFile file, String key) {
        Assert.notNull(file, "--->>>file 为空");
        String originalName = file.getOriginalFilename();
        String suffix = originalName != null ? originalName.substring(originalName.lastIndexOf(".")) : ".jpg";
        String fileName =
            StrUtil.isBlank(key) ? appName + "/" + getOpenId() + "/" + UUID.randomUUID().toString() + suffix : key;
        try {
            FileInputStream inputStream = (FileInputStream) file.getInputStream();
            String url = QiNiuUtil.upload2QiNiuCloud(inputStream, fileName);
            log.info("--->>>七牛云文件上传成功,文件外链地址:[{}]", url);
            if (null != url) {
                return new ResponseEntity<>(Message.success(MapUtil.of("url", String.format("%s", url))), HttpStatus.OK);
            }
        } catch (IOException e) {
            log.error("--->>>文件转换出错:{}", e.getMessage());
        }
        throw new AppRuntimeException(WebResultCode.FILE_UPLOAD_ERROR);
    }
}
