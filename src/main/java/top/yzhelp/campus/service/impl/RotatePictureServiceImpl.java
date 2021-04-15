package top.yzhelp.campus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.yzhelp.campus.mapper.RotatePictureMapper;
import top.yzhelp.campus.model.other.RotatePicture;
import top.yzhelp.campus.service.RotatePictureService;

import java.util.List;

/**
 * @author <a href="https://github.com/gongsir0630">码之泪殇</a>
 * @date 2021/4/8 15:49
 * 你的指尖,拥有改变世界的力量
 * @description description
 */
@Service
public class RotatePictureServiceImpl extends ServiceImpl<RotatePictureMapper,RotatePicture> implements RotatePictureService {
  /**
   * 小程序端获取 Banner 展示,通过 type 类型匹配
   *
   * @param rotateType 轮播图类型: 首页,内推(订阅,广告),柚子帮
   * @return 轮播图列表
   */
  @Override
  public List<RotatePicture> getBannersByMiniApp(String rotateType) {
    return this.list(new QueryWrapper<RotatePicture>().eq("rotate_type",rotateType));
  }
}
