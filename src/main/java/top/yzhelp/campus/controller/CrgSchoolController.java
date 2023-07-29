package top.yzhelp.campus.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import top.yzhelp.campus.enums.CrgResultCode;
import top.yzhelp.campus.exception.CrgApiException;
import top.yzhelp.campus.model.common.CrgWebResponse;
import top.yzhelp.campus.service.CrgSchoolService;

/**
 * @author kyle <gongsir0630@gmail.com>
 * Created on 2023/7/29
 */
@RestController
@RequestMapping("/wx/school")
@Api(tags = "小程序端-学校信息接口")
public class CrgSchoolController {
    private final Logger logger = LoggerFactory.getLogger(CrgSchoolController.class);

    @Autowired
    private CrgSchoolService crgSchoolService;

    @GetMapping("/list")
    public CrgWebResponse<?> list() {
        return CrgWebResponse.success(crgSchoolService.list());
    }

    @GetMapping("/{id}")
    public CrgWebResponse<?> getSchoolById(@PathVariable Long id) {
        logger.info("getSchoolById, id==>{}", id);
        if (id == null || id <= 0) {
            throw new CrgApiException(CrgResultCode.LOGIN_FAIL, "id为空");
        }
        return CrgWebResponse.success(crgSchoolService.getById(id));
    }
}
