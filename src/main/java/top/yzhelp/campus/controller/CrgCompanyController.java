package top.yzhelp.campus.controller;

import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yzhelp.campus.enums.CrgResultCode;
import top.yzhelp.campus.exception.CrgApiException;
import top.yzhelp.campus.model.CrgWebResponse;
import top.yzhelp.campus.service.CrgCompanyService;

/**
 * @author 码之泪殇 <gongsir0630@gmail.com>
 * Created on 2022-02-12
 */
@RestController
@RequestMapping("/wx/company")
@Api(tags = "小程序端-企业信息接口")
public class CrgCompanyController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final CrgCompanyService crgCompanyService;

    @Autowired
    public CrgCompanyController(CrgCompanyService crgCompanyService) {
        this.crgCompanyService = crgCompanyService;
    }

    @GetMapping("/list")
    public CrgWebResponse<?> list() {
        return CrgWebResponse.success(crgCompanyService.queryAll());
    }

    @GetMapping("/{id}")
    public CrgWebResponse<?> one(@PathVariable Long id) {
        logger.info("one, id===>{}", id);
        if (id == null || id <= 0) {
            throw new CrgApiException(CrgResultCode.LOGIN_FAIL, "id为空");
        }
        return CrgWebResponse.success(crgCompanyService.queryById(id));
    }
}
