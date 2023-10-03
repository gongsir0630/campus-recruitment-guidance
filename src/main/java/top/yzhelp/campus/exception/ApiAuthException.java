package top.yzhelp.campus.exception;

import lombok.Getter;
import top.yzhelp.campus.enums.WebResultCode;

/**
 * @author <a href="https://github.com/gongsir0630">Kyle</a>
 * @date 2021/3/29 12:34
 * 你的指尖,拥有改变世界的力量
 * @description 自定义异常, 用于处理Api认证失败异常信息保存
 */
@Getter
public class ApiAuthException extends RuntimeException {

    private final WebResultCode code;
    private final String message;

    public ApiAuthException(WebResultCode code) {
        super(code.getErrMsg());
        this.code = code;
        this.message = this.code.getErrMsg();
    }

    public ApiAuthException(WebResultCode code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public WebResultCode getCodeMsg() {
        return code;
    }

}
