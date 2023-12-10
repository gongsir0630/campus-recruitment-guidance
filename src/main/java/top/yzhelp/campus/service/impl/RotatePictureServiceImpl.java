package top.yzhelp.campus.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import top.yzhelp.campus.mapper.RotatePictureMapper;
import top.yzhelp.campus.model.RotatePicture;
import top.yzhelp.campus.service.RotatePictureService;

/**
 * @author <a href="https://github.com/gongsir0630">Kyle</a>
 * @date 2021/4/8 15:49
 * @description description
 */
@Service
public class RotatePictureServiceImpl extends ServiceImpl<RotatePictureMapper, RotatePicture>
    implements RotatePictureService {
    /**
     * 小程序端获取 Banner 展示,通过 type 类型匹配
     *
     * @param rotateType 轮播图类型: 首页,内推(订阅,广告),柚子帮
     * @return 轮播图列表
     */
    @Override
    public List<RotatePicture> getBannersByMiniApp(String rotateType) {
        return this.list(new QueryWrapper<RotatePicture>().eq("rotate_type", rotateType));
    }

    /**
     * 获取所有数据
     *
     * @return 轮播图
     */
    @Override
    public IPage<RotatePicture> getAllPics() {
        return this.page(null, new LambdaQueryWrapper<RotatePicture>().orderByDesc(RotatePicture::getId));
    }

    /**
     * 更新
     *
     * @param picture 图片信息
     */
    @Override
    public void updatePictureById(RotatePicture picture) {
        this.updateById(picture);
    }
}
