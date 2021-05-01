package top.yzhelp.campus.exception;

import top.yzhelp.campus.controller.res.CodeMsg;

/**
 * @author <a href="https://github.com/gongsir0630">码之泪殇</a>
 * @date 2021/3/29 12:34
 * 你的指尖,拥有改变世界的力量
 * @description 自定义异常, 用于处理Api认证失败异常信息保存
 */
public class ApiAuthException extends RuntimeException {

  private CodeMsg codeMsg;

  public ApiAuthException() {
    super();
  }

  public ApiAuthException(CodeMsg codeMsg) {
    super(codeMsg.getErrMsg());
    this.codeMsg = codeMsg;
  }

  public CodeMsg getCodeMsg() {
    return codeMsg;
  }

  public void setCodeMsg(CodeMsg codeMsg) {
    this.codeMsg = codeMsg;
  }
}
