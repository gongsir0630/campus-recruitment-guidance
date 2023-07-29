package top.yzhelp.campus.model.basic;

import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * @author kyle <gongsir0630@gmail.com>
 * Created on 2022-02-12
 */
@Data
@TableName("crg_school")
public class CrgSchool {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * logo_url: eg. {@code https://cdn.xxx.com/xxx.png}
     */
    private String logo;

    /**
     * 学校名称: eg.清华大学
     */
    private String name;

    /**
     * 专业列表: eg.["计算机科学与技术", "软件工程"]
     */
    private String majorJson;

    /**
     * majorJson 的 dto 形式, db不会存在该字段
     */
    private List<String> majorList;

    /**
     * 邮箱后缀: eg.@stu.swpu.edu.cn
     */
    private String mailSuffix;
}
