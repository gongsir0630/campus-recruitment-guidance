package top.yzhelp.campus.service;

import com.baomidou.mybatisplus.extension.service.IService;

import top.yzhelp.campus.model.SecretKey;

/**
 * @author <a href="https://github.com/gongsir0630">Kyle</a>
 * @date 2021/4/10 19:38
 * @description 秘钥信息接口
 */
public interface SecretKeyService extends IService<SecretKey> {
    /**
     * 通过平台名称获取 ak 和 sk
     *
     * @param name 名称
     * @return 秘钥信息
     */
    SecretKey getSkByPlatName(String name);
}
