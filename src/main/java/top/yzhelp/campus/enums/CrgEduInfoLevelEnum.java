package top.yzhelp.campus.enums;

/**
 * @author 码之泪殇 <gongsir0630@gmail.com>
 * Created on 2022-02-12
 */
public enum CrgEduInfoLevelEnum {

    ;

    private final int val;
    private final String desc;

    CrgEduInfoLevelEnum(int val, String desc) {
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
