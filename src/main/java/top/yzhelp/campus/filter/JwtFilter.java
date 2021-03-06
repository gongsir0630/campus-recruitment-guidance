package top.yzhelp.campus.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import top.yzhelp.campus.shiro.vo.JwtToken;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author <a href="https://github.com/gongsir0630">码之泪殇</a>
 * @date 2021/3/29 19:47
 * 你的指尖,拥有改变世界的力量
 * @description jwt 核心过滤器
 * 执行流程 preHandle->isAccessAllowed->isLoginAttempt->executeLogin
 */
@Slf4j
public class JwtFilter extends BasicHttpAuthenticationFilter {
  /**
   * 跨域支持
   * @param request 请求
   * @param response 相应
   * @return bool
   * @throws Exception 异常
   */
  @Override
  protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
    HttpServletRequest httpServletRequest = (HttpServletRequest) request;
    HttpServletResponse httpServletResponse = (HttpServletResponse) response;
    httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
    httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
    httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
    // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
    if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
      httpServletResponse.setStatus(HttpStatus.OK.value());
      return false;
    }
    return super.preHandle(request, response);
  }

  @Override
  protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
    // 判断request是否包含 Authorization 字段
    String auth = getAuthzHeader(request);
    return auth != null && !"".equals(auth);
  }

  @Override
  protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
    if (isLoginAttempt(request,response)) {
      // executeLogin 进入登录逻辑
      // 从request请求头获取 Authorization 字段
      String token = getAuthzHeader(request);
      log.info("--->>>JwtFilter::isAccessAllowed拦截到认证token信息:[{}]",token);
      getSubject(request,response).login(new JwtToken(token));
    }
    return true;
  }
}
