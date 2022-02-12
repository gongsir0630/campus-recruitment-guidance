package top.yzhelp.campus.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author 码之泪殇 <gongsir0630@gmail.com>
 * Created on 2022-02-12
 */
@Data
@TableName(value = "crg_user")
public class CrgUser {

    /**
     * 用户openId, 唯一键
     */
    @TableId(type = IdType.INPUT)
    private String openId;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 头像url
     */
    private String avatar;

    /**
     * 性别
     */
    private String gender;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 电话
     */
    private String phoneNumber;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 个人简介
     */
    private String profile;
}
