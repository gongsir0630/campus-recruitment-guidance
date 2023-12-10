package top.yzhelp.campus.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * @author <a href="https://github.com/gongsir0630">Kyle</a>
 * @date 2021/3/29 12:40
 * @description 小程序信息 🏷
 */
@Data
@TableName("crg_app_info")
public class CrgAppInfo {
    @TableId(type = IdType.AUTO)
    private long id;

    private String about;

    private String joinUs;
}
