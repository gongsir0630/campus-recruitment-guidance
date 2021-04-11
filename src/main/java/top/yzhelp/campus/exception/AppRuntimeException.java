package top.yzhelp.campus.exception;

/**
 * @author <a href="https://github.com/gongsir0630">码之泪殇</a>
 * @date 2021/4/10 19:57
 * 你的指尖,拥有改变世界的力量
 * @description description
 */
public class AppRuntimeException extends RuntimeException {
  private static final long serialVersionUID = 4881698471192264412L;

  public AppRuntimeException(Throwable e) {
    super(e);
  }

  public AppRuntimeException(String msg) {
    super(msg);
  }

  public AppRuntimeException(String msg, Throwable e) {
    super(msg, e);
  }
}
