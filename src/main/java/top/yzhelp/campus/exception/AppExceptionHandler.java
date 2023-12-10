package top.yzhelp.campus.exception;

import static com.alibaba.fastjson.JSON.toJSON;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.ShiroException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.google.common.collect.Maps;

import top.yzhelp.campus.enums.WebResultCode;
import top.yzhelp.campus.model.common.Message;

/**
 * @author <a href="https://github.com/gongsir0630">Kyle</a>
 * @date 2021/3/29 12:36
 * @description 全局异常处理
 */
@RestControllerAdvice
public class AppExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 处理 Shiro 异常
     *
     * @param e 异常信息
     * @return json
     */
    @ExceptionHandler({ShiroException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Message<?> handShiroException(HttpServletRequest request, ShiroException e) {
        printStackTrace(request, e);
        return Message.fail(WebResultCode.SHIRO_ERROR);
    }

    /**
     * 处理 自定义ApiAuthException异常
     *
     * @param e 异常信息
     * @return json
     */
    @ExceptionHandler({ApiAuthException.class})
    public Message<?> handApiAuthException(HttpServletRequest request, ApiAuthException e) {
        printStackTrace(request, e);
        return Message.fail(e.getCode(), e.getMessage());
    }

    @ExceptionHandler({AppRuntimeException.class})
    public Message<?> handAppException(HttpServletRequest request, AppRuntimeException e) {
        printStackTrace(request, e);
        return Message.fail(e.getCode(), e.getMessage());
    }

    @ExceptionHandler({AppRuntimeException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Message<?> handThrowable(HttpServletRequest request, Throwable e) {
        printStackTrace(request, e);
        return Message.fail(WebResultCode.SERVICE_BUSY, e.getMessage());
    }

    /**
     * 打印异常堆栈和入参
     */
    private void printStackTrace(HttpServletRequest request, Throwable e) {
        Map<String, Object> map = Maps.newHashMap();
        Map<String, String[]> params = request.getParameterMap();
        map.putAll(Maps.transformValues(
            params, arr -> (arr == null || arr.length == 0) ? null : arr.length == 1 ? arr[0] : arr)
        );
        logger.error("handle===>{},  msg===>{}, params===>{}, cookies===>{}, uri===>{}",
            e.getClass().getName(), e.getMessage(), toJSON(map), toJSON(request.getCookies()), request.getRequestURI(), e);
    }
}
