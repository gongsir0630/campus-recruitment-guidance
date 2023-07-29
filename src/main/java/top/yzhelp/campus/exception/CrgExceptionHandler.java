package top.yzhelp.campus.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import top.yzhelp.campus.model.common.CrgWebResponse;

/**
 * @author kyle <gongsir0630@gmail.com>
 * Created on 2022-02-12
 */
@RestControllerAdvice
public class CrgExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 只处理自定义api异常
     *
     * @param e 异常
     * @return CrgWebResponse
     */
    @ExceptionHandler(value = {CrgApiException.class})
    public CrgWebResponse<?> handleApiException(CrgApiException e) {
        logger.error("api异常, 异常详情信息code==>{}, errMsg==>{}", e.getCode(), e.getMsg(), e);
        return CrgWebResponse.err(e.getCode(), e.getMsg());
    }

    /**
     * 兜底异常处理
     *
     * @param e 异常信息
     * @return CrgWebResponse
     */
    @ExceptionHandler(value = {RuntimeException.class})
    public CrgWebResponse<?> handleRuntimeException(RuntimeException e) {
        logger.error("服务发生异常", e);
        return CrgWebResponse.err(500, "服务异常, 请稍后重试");
    }
}
