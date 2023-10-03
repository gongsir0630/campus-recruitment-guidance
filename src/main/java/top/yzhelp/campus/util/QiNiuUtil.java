package top.yzhelp.campus.util;

import java.io.FileInputStream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import top.yzhelp.campus.enums.WebResultCode;
import top.yzhelp.campus.exception.AppRuntimeException;
import top.yzhelp.campus.model.SecretKey;
import top.yzhelp.campus.service.SecretKeyService;

/**
 * @author <a href="https://github.com/gongsir0630">Kyle</a>
 * @date 2021/4/10 19:13
 * 你的指尖,拥有改变世界的力量
 * @description 七牛云文件存储
 */
@Component
@Slf4j
public class QiNiuUtil {
    /**
     * 存储空间名称
     */
    public static final String PLAT_NAME = "七牛云";

    /**
     * cdn 地址
     */
    private static final String URL_PREFIX = "https://cdn.yzhelp.top/";

    /**
     * 存储空间名称
     */
    public static final String BUCKET_NAME = "yzhelp";

    /**
     * AccessKey
     */
    private static String ACCESS_KEY;

    /**
     * SecretKey
     */
    private static String SECRET_KEY;

    private final SecretKeyService secretKeyService;

    @Autowired
    public QiNiuUtil(SecretKeyService secretKeyService1) {
        this.secretKeyService = secretKeyService1;
    }

    /**
     * 上传文件至七牛云存储
     *
     * @param file 文件
     * @param fileName 文件名称
     * @return 七牛云外链
     */
    public static String upload2QiNiuCloud(FileInputStream file, String fileName) {
        Configuration cfg = new Configuration(Region.huanan());
        UploadManager uploadManager = new UploadManager(cfg);

        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        String upToken = auth.uploadToken(BUCKET_NAME);
        try {
            Response response = uploadManager.put(file, fileName, upToken, null, null);
            DefaultPutRet putRet = JSONObject.parseObject(response.bodyString(), DefaultPutRet.class);
            String hash = putRet.hash;
            if (!StrUtil.isBlank(hash)) {
                // 文件上传成功, 返回 url 链接
                return URL_PREFIX + putRet.key;
            }
        } catch (QiniuException exception) {
            log.error("--->>>七牛云文件上传服务出错啦~");
        }
        return null;
    }

    @PostConstruct
    public void init() {
        log.info("--->>>初始化七牛云秘钥信息<<<---");
        SecretKey key = secretKeyService.getSkByPlatName(PLAT_NAME);
        if (null == key) {
            throw new AppRuntimeException(WebResultCode.AUTH_OPERATE_FAIL, "无相关平台秘钥信息");
        }
        ACCESS_KEY = key.getAk();
        SECRET_KEY = key.getSk();
    }
}
