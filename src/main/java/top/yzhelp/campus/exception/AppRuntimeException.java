package top.yzhelp.campus.exception;

import lombok.Getter;
import top.yzhelp.campus.enums.WebResultCode;

/**
 * @author <a href="https://github.com/gongsir0630">Kyle</a>
 * @date 2021/4/10 19:57
 * @description description
 */
@Getter
public class AppRuntimeException extends RuntimeException {
    private final WebResultCode code;
    private final String message;

    public AppRuntimeException(WebResultCode code) {
        super(code.getErrMsg());
        this.code = code;
        this.message = this.code.getErrMsg();
    }

    public AppRuntimeException(WebResultCode code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
}
