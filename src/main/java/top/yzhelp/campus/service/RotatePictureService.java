package top.yzhelp.campus.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import top.yzhelp.campus.model.RotatePicture;

/**
 * @author <a href="https://github.com/gongsir0630">Kyle</a>
 * @date 2021/4/8 15:48
 * 你的指尖,拥有改变世界的力量
 * @description 小程序轮播图接口
 */
public interface RotatePictureService extends IService<RotatePicture> {
    /**
     * 小程序端获取 Banner 展示,通过 type 类型匹配
     *
     * @param rotateType 轮播图类型: 首页,内推(订阅,广告),柚子帮
     * @return 轮播图列表
     */
    List<RotatePicture> getBannersByMiniApp(String rotateType);

    /**
     * 获取所有数据
     *
     * @return 轮播图
     */
    IPage<RotatePicture> getAllPics();

    /**
     * 更新
     *
     * @param picture 图片信息
     */
    void updatePictureById(RotatePicture picture);
}
