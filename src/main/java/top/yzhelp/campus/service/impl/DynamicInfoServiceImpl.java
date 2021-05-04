package top.yzhelp.campus.service.impl;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.yzhelp.campus.mapper.DynamicInfoMapper;
import top.yzhelp.campus.model.dt.DynamicInfo;
import top.yzhelp.campus.service.DynamicInfoService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="https://github.com/gongsir0630">码之泪殇</a>
 * @date 2021/4/8 08:58
 * 你的指尖,拥有改变世界的力量
 * @description 小程序动态功能模块 -> 接口实现
 */
@Service
public class DynamicInfoServiceImpl extends ServiceImpl<DynamicInfoMapper, DynamicInfo> implements DynamicInfoService {

  /**
   * 获取所有动态列表
   *
   * @return list
   */
  @Override
  public List<DynamicInfo> getAllDtInfoList() {
    return this.list(new QueryWrapper<DynamicInfo>().orderByDesc("id"));
  }

  /**
   * 点赞动态
   *
   * @param id 动态 id
   * @param openId 用户
   */
  @Override
  public void likeById(int id,String openId) {
    // 更新当前动态的点赞列表
    DynamicInfo dt = this.getDtDetailById(id);
    ArrayList<String> likes = !StrUtil.isBlank(dt.getLikeList())
      ? ListUtil.toList(dt.getLikeList().split(","))
      : new ArrayList<>();
    // 更新用户自己的点赞列表
    if (!likes.contains(openId)) {
      // 点赞
      likes.add(openId);
    } else {
      // 取消点赞
      likes.remove(openId);
    }
    dt.setLikeList(String.join(",",likes));
    this.updateById(dt);
  }

  /**
   * 动态详情
   *
   * @param id 动态 id
   * @return 动态详情
   */
  @Override
  public DynamicInfo getDtDetailById(int id) {
    return this.getById(id);
  }

  /**
   * 收藏
   *
   * @param id     动态 id
   * @param openId 用户
   */
  @Override
  public void collection(int id, String openId) {
    // 更新当前动态的点赞列表
    DynamicInfo dt = this.getDtDetailById(id);
    ArrayList<String> collections = !StrUtil.isBlank(dt.getCollectionList())
      ? ListUtil.toList(dt.getCollectionList().split(","))
      : new ArrayList<>();
    if (!collections.contains(openId)) {
      // 收藏
      collections.add(openId);
    } else {
      // 取消点赞
      collections.remove(openId);
    }
    dt.setCollectionList(String.join(",",collections));
    this.updateById(dt);
  }

  /**
   * 删除动态
   *
   * @param id 动态 id
   */
  @Override
  public void deleteDtById(int id) {
    // 删除动态
    this.removeById(id);
  }

  /**
   * 发布 | 更新
   *
   * @param info 动态信息
   * @return 动态详情
   */
  @Override
  public DynamicInfo saveOrUpdateDt(DynamicInfo info) {
    this.saveOrUpdate(info);
    return this.getDtDetailById(info.getId());
  }
}
