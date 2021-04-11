package top.yzhelp.campus.controller.res;

/**
 * @author <a href="https://github.com/gongsir0630">码之泪殇</a>
 * @date 2021/3/29 12:27
 * 你的指尖,拥有改变世界的力量
 * @description description
 */
public class CodeMsg {
  private final int code;
  private final String errMsg;

  public static CodeMsg SUCCESS = new CodeMsg(0,"success");

  public static CodeMsg LOGIN_FAIL = new CodeMsg(-1,"code2session failure, please try aging");
  public static CodeMsg NO_USER = new CodeMsg(1000,"user not found");

  public static CodeMsg TOKEN_ERROR = new CodeMsg(401,"token is invalid");
  public static CodeMsg SHIRO_ERROR = new CodeMsg(401,"token is invalid");

  public static CodeMsg FIE_UPLOAD_ERROR = new CodeMsg(2000,"file upload fail, please retry");

  public CodeMsg(int code, String errMsg) {
    this.code=code;
    this.errMsg = errMsg;
  }

  public int getCode() {
    return code;
  }

  public String getErrMsg() {
    return errMsg;
  }

  @Override
  public String toString() {
    return "CodeMsg{" +
      "code=" + code +
      ", errMsg='" + errMsg + '\'' +
      '}';
  }

}

