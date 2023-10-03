package top.yzhelp.campus.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import top.yzhelp.campus.mapper.AppInfoMapper;
import top.yzhelp.campus.model.CrgAppInfo;
import top.yzhelp.campus.service.AppInfoService;

/**
 * @author <a href="https://github.com/gongsir0630">Kyle</a>
 * @date 2021/4/8 09:34
 * 你的指尖,拥有改变世界的力量
 * @description 小程序信息接口实现
 */
@Service
public class AppInfoServiceImpl extends ServiceImpl<AppInfoMapper, CrgAppInfo> implements AppInfoService {
}
