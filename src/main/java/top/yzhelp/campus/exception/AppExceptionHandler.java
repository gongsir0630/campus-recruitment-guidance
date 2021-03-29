package top.yzhelp.campus.exception;

import org.apache.shiro.ShiroException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.yzhelp.campus.controller.res.CodeMsg;
import top.yzhelp.campus.controller.res.Result;

/**
 * @author <a href="https://github.com/gongsir0630">码之泪殇</a>
 * @date 2021/3/29 12:36
 * 你的指尖,拥有改变世界的力量
 * @description 全局异常处理
 */
@RestControllerAdvice
public class AppExceptionHandler {

  private final Logger log = LoggerFactory.getLogger(this.getClass());

  /**
   * 处理 Shiro 异常
   * @param e 异常信息
   * @return json
   */
  @ExceptionHandler({ShiroException.class})
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public ResponseEntity<Result<String>> handShiroException(ShiroException e) {
    this.log.error("--->>> 捕捉到 [ApiAuthException] 异常: {}", e.getMessage());
    return new ResponseEntity<>(Result.fail(CodeMsg.SHIRO_ERROR,null), HttpStatus.UNAUTHORIZED);
  }

  /**
   * 处理 自定义ApiAuthException异常
   * @param e 异常信息
   * @return json
   */
  @ExceptionHandler({ApiAuthException.class})
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public ResponseEntity<Result<String>> handApiAuthException(ApiAuthException e) {
    log.error("--->>> 捕捉到 [ApiAuthException] 异常: {}-{}",e.getCodeMsg().getCode(),e.getCodeMsg().getErrMsg() );
    return new ResponseEntity<>(Result.fail(e.getCodeMsg(),null), HttpStatus.UNAUTHORIZED);
  }

}
