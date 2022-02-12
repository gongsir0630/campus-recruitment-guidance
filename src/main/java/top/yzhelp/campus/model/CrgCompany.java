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
@TableName(value = "crg_company")
public class CrgCompany {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * logo_url
     */
    private String logo;

    /**
     * 企业/公司名称: eg.快手
     */
    private String name;

    /**
     * slogan: eg.拥抱每一种生活
     */
    private String slogan;

    private String mailSuffix;
}
