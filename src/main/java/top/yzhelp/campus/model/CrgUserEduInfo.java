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
@TableName(value = "crg_user_edu_info")
public class CrgUserEduInfo {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户openId
     */
    private String openId;

    /**
     * 学校id
     */
    private long schoolId;

    /**
     * 所在专业
     */
    private String major;

    /**
     * 入学年份
     */
    private String entrance;

    /**
     * 毕业年份
     */
    private String graduate;

    /**
     * 学历
     * @see top.yzhelp.campus.enums.CrgEduInfoLevelEnum
     */
    private int level;

    /**
     * 经历描述
     */
    private String description;

    /**
     * 信息认证状态
     * @see top.yzhelp.campus.enums.CrgCertificationStatusEnum
     */
    private int status;
}
