package top.yzhelp.campus.enums;

/**
 * @author kyle <gongsir0630@gmail.com>
 * Created on 2022-02-12
 */
public enum CrgCertificationStatusEnum {

    ;

    private final int val;
    private final String desc;

    CrgCertificationStatusEnum(int val, String desc) {
        this.val = val;
        this.desc = desc;
    }

    public int getVal() {
        return val;
    }

    public String getDesc() {
        return desc;
    }
}
