package top.yzhelp.campus.enums;

import lombok.Getter;

/**
 * @author <a href="https://github.com/gongsir0630">Kyle</a>
 * @date 2021/3/29 12:27
 * 你的指尖,拥有改变世界的力量
 * @description description
 */
@Getter
public enum WebResultCode {
    SUCCESS(0, "success"),
    LOGIN_FAIL(-1, "code2session failure, please try aging"),
    SHIRO_ERROR(401, "token is invalid"),
    SERVICE_BUSY(500, "内部系统繁忙"),
    NO_USER(1000, "user not found"),
    AUTH_OPERATE_FAIL(1001, "操作失败"),
    FILE_UPLOAD_ERROR(2000, "file upload fail, please retry"),
    ;

    private final int code;
    private final String errMsg;

    WebResultCode(int code, String errMsg) {
        this.code = code;
        this.errMsg = errMsg;
    }

}

