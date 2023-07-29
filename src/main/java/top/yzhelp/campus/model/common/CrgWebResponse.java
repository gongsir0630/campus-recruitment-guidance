package top.yzhelp.campus.model.common;

import lombok.Data;
import top.yzhelp.campus.enums.CrgResultCode;

/**
 * @author kyle <gongsir0630@gmail.com>
 * Created on 2022-02-12
 */
@Data
public class CrgWebResponse<T> {

    private int code;

    private String errMsg;

    private T data;

    private CrgWebResponse(CrgResultCode resultCode, T data) {
        this.code = resultCode.getCode();
        this.errMsg = resultCode.getDesc();
    }

    private CrgWebResponse(int code, String errMsg) {
        this.code = code;
        this.errMsg = errMsg;
    }

    public static <T> CrgWebResponse<T> success(T data) {
        return new CrgWebResponse<>(CrgResultCode.SUCCESS, data);
    }

    public static <T> CrgWebResponse<T> fail(CrgResultCode resultCode, T data) {
        return new CrgWebResponse<>(resultCode, data);
    }

    public static <T> CrgWebResponse<T> err(int code, String errMsg) {
        return new CrgWebResponse<>(code, errMsg);
    }
}
