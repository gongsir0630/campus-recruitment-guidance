package top.yzhelp.campus.exception;

import org.apache.commons.lang3.StringUtils;

import top.yzhelp.campus.enums.CrgResultCode;

/**
 * @author kyle <gongsir0630@gmail.com>
 * Created on 2022-02-12
 */
public class CrgApiException extends RuntimeException {

    private int code;
    private String msg;

    public CrgApiException() {
        super();
    }

    public CrgApiException(CrgResultCode errCode, String errMsg) {
        super(getErrMsg(errCode, errMsg));
        this.code = errCode.getCode();
        this.msg = getErrMsg(errCode, errMsg);
    }

    private static String getErrMsg(CrgResultCode errCode, String errMsg) {
        return StringUtils.defaultIfBlank(errMsg, errCode.getDesc());
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
