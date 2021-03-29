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
  public static CodeMsg SESSION_KEY_ERROR = new CodeMsg(1001,"sessionKey is invalid");
  public static CodeMsg TOKEN_ERROR = new CodeMsg(1002,"token is invalid");
  public static CodeMsg SHIRO_ERROR = new CodeMsg(1003,"token is invalid");

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

