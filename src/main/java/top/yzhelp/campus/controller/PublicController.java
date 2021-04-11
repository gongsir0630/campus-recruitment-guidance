package top.yzhelp.campus.controller;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import top.yzhelp.campus.controller.res.CodeMsg;
import top.yzhelp.campus.controller.res.Result;
import top.yzhelp.campus.shiro.ShiroRealm;
import top.yzhelp.campus.util.QiNiuUtil;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

/**
 * @author <a href="https://github.com/gongsir0630">码之泪殇</a>
 * @date 2021/4/10 22:32
 * 你的指尖,拥有改变世界的力量
 * @description 公开接口
 */
@RestController
@Slf4j
@RequestMapping("public")
@Api(tags = "公开接口,无需token认证")
public class PublicController {

  @Value("${spring.application.name}")
  private String appName;

  public String getOpenId() {
    return ShiroRealm.getShiroAccount().getAuthName();
  }

  /**
   * 图片上传通用接口
   * @param file 图片二进制文件
   * @param key 文件唯一 key, 默认服务端自动生成,也可以前端自定义
   * @return 图片 url 地址
   */
  @PostMapping(path = "/upload")
  @ApiOperation("图片上传")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "file",value = "图片文件",required = true),
    @ApiImplicitParam(name = "key",value = "文件唯一 key, 默认服务端自动生成,也可以前端自定义",required = false)
  })
  @ApiResponses({
    @ApiResponse(code = 200,message = "接口调用成功")
  })
  public ResponseEntity<Result<Map<String,Object>>> uploadFile(MultipartFile file,String key) {
    Assert.notNull(file,"--->>>file 为空");
    String originalName = file.getOriginalFilename();
    String suffix = originalName != null ? originalName.substring(originalName.lastIndexOf(".")) : ".jpg";
    String fileName = StrUtil.isBlank(key) ? appName+"/"+getOpenId()+"/"+ UUID.randomUUID().toString()+suffix : key;
    try {
      FileInputStream inputStream = (FileInputStream) file.getInputStream();
      String url = QiNiuUtil.upload2QiNiuCloud(inputStream, fileName);
      log.info("--->>>七牛云文件上传成功,文件外链地址:[{}]",url);
      if (null != url) {
        return new ResponseEntity<>(Result.success(MapUtil.of("url",String.format("%s",url))),HttpStatus.OK);
      }
    } catch (IOException e) {
      log.error("--->>>文件转换出错:{}",e.getMessage());
    }
    return new ResponseEntity<>(Result.fail(CodeMsg.FIE_UPLOAD_ERROR,null), HttpStatus.OK);
  }
}
