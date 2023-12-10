package top.yzhelp.campus.model.common;

import org.apache.commons.lang3.StringUtils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import top.yzhelp.campus.enums.WebResultCode;

/**
 * @param <T> data 泛型
 * @author <a href="https://github.com/gongsir0630">Kyle</a>
 * @date 2021/3/29 12:30
 * @description 返回集封装
 */
@Getter
@ApiModel("api 数据返回集")
public class Message<T> {
    @ApiModelProperty(value = "响应代码: 0 [操作成功], 1000 [用户不存在], 401 [登录失效]")
    private final int code;
    @ApiModelProperty(value = "提示信息")
    private final String errMsg;
    @ApiModelProperty(value = "响应数据")
    private final T data;

    private Message(WebResultCode result, String message, T data) {
        this.code = result.getCode();
        this.errMsg = StringUtils.defaultString(message, result.getErrMsg());
        this.data = data;
    }

    public static Message<Void> success() {
        return new Message<>(WebResultCode.SUCCESS, null, null);
    }

    public static <T> Message<T> success(T data) {
        return new Message<>(WebResultCode.SUCCESS, null, data);
    }

    public static Message<Void> fail(WebResultCode result) {
        return new Message<>(result, null, null);
    }

    public static Message<Void> fail(WebResultCode result, String message) {
        return new Message<>(result, message, null);
    }

    public static <T> Message<T> fail(WebResultCode result, T data) {
        return new Message<>(result, null, data);
    }

    public static <T> Message<T> fail(WebResultCode result, String message, T data) {
        return new Message<>(result, message, data);
    }

}

