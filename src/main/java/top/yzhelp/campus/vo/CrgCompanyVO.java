package top.yzhelp.campus.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @author 码之泪殇 <gongsir0630@gmail.com>
 * Created on 2022-02-12
 */
@Data
@Builder
@ApiModel("公司信息详情")
public class CrgCompanyVO {

    @ApiModelProperty(value = "企业id", example = "1")
    private Long id;

    @ApiModelProperty(value = "企业logo-url链接")
    private String logo;

    @ApiModelProperty(value = "企业名称")
    private String name;

    @ApiModelProperty(value = "企业slogan")
    private String slogan;

    @ApiModelProperty(value = "企业官方邮箱后缀")
    private String mailSuffix;
}
