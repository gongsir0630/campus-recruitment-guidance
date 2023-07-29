package top.yzhelp.campus.enums;

/**
 * @author kyle <gongsir0630@gmail.com>
 * Created on 2022-02-12
 */
public enum CrgResultCode {
    /**
     * 成功
     */
    SUCCESS(0, "success"),
    /**
     * 登录失败
     */
    LOGIN_FAIL(-1, "code2session fail, please try again"),
    ;

    private final int code;
    private final String desc;

    CrgResultCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
