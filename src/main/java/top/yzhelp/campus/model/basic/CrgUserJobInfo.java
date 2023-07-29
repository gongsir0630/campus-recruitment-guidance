package top.yzhelp.campus.model.basic;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * @author kyle <gongsir0630@gmail.com>
 * Created on 2022-02-12
 */
@Data
@TableName(value = "crg_user_job_info")
public class CrgUserJobInfo {

    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户openId
     */
    private String openId;

    /**
     * 所在公司id
     */
    private Integer companyId;

    /**
     * 所在部门
     */
    private String department;

    /**
     * 职位名称
     */
    private String jobTitle;

    /**
     * 经历描述
     */
    private String description;

    /**
     * 认证状态
     *
     * @see top.yzhelp.campus.enums.CrgCertificationStatusEnum
     */
    private String status;
}
