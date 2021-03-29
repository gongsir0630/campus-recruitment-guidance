package top.yzhelp.campus.controller.res;

/**
 * @author <a href="https://github.com/gongsir0630">码之泪殇</a>
 * @date 2021/3/29 12:30
 * 你的指尖,拥有改变世界的力量
 * @description 返回集封装
 * @param <T> data 泛型
 */
public class Result<T> {

  private int code;
  private String errMsg;
  private T data;

  private Result(CodeMsg mg, T data) {
    if (mg==null){
      return;
    }
    this.code=mg.getCode();
    this.errMsg =mg.getErrMsg();
    this.data=data;
  }


  /**
   * 成功时
   * @param <T> data泛型
   * @return Result
   */
  public static <T>  Result<T>  success(T data){
    return new Result<T>(CodeMsg.SUCCESS,data);
  }

  /**
   * 失败
   * @param <T> data泛型
   * @return Result
   */
  public static <T>  Result<T>  fail(CodeMsg mg, T data){
    return new Result<T>(mg,data);
  }

  public int getCode() {
    return code;
  }


  public String getErrMsg() {
    return errMsg;
  }


  public T getData() {
    return data;
  }
}

