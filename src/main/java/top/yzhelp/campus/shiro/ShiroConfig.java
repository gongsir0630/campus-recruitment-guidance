package top.yzhelp.campus.shiro;

import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import top.yzhelp.campus.filter.JwtFilter;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author <a href="https://github.com/gongsir0630">码之泪殇</a>
 * @date 2021/3/29 20:12
 * 你的指尖,拥有改变世界的力量
 * @description shiro核心配置
 */
@Configuration
public class ShiroConfig {
  /**
   * SecurityManager,安全管理器,所有与安全相关的操作都会与之进行交互;
   * 它管理着所有Subject,所有Subject都绑定到SecurityManager,与Subject的所有交互都会委托给SecurityManager
   * DefaultWebSecurityManager :
   * 会创建默认的DefaultSubjectDAO(它又会默认创建DefaultSessionStorageEvaluator)
   * 会默认创建DefaultWebSubjectFactory
   * 会默认创建ModularRealmAuthenticator
   */
  @Bean
  public DefaultWebSecurityManager securityManager(ShiroRealm shiroRealm) {
    DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
    // 设置realms
    securityManager.setRealms(shiroRealm.allRealm());
    // close session
    DefaultSubjectDAO defaultSubjectDAO = (DefaultSubjectDAO) securityManager.getSubjectDAO();
    DefaultSessionStorageEvaluator evaluator = (DefaultSessionStorageEvaluator) defaultSubjectDAO.getSessionStorageEvaluator();
    evaluator.setSessionStorageEnabled(Boolean.FALSE);
    defaultSubjectDAO.setSessionStorageEvaluator(evaluator);
    return securityManager;
  }

  /**
   * 配置Shiro的访问策略
   */
  @Bean
  public ShiroFilterFactoryBean filterFactoryBean(DefaultWebSecurityManager securityManager) {
    ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
    // 重要，设置自定义拦截器，当访问某些自定义url时，使用这个filter进行验证
    Map<String, Filter> filterMap = new LinkedHashMap<>(8);
    filterMap.put("jwt", new JwtFilter());
    factoryBean.setFilters(filterMap);
    factoryBean.setSecurityManager(securityManager);

    Map<String, String> filterRuleMap = new LinkedHashMap<>(8);
    //登陆相关api不需要被过滤器拦截
    filterRuleMap.put("/wx/user/login/**", "anon");
    filterRuleMap.put("/admin/user/login/**", "anon");
    // 可以配置多个filter，用逗号分隔，按顺序过滤
    // 所有请求通过 jwt Filter
    filterRuleMap.put("/**", "authc,jwt");
    factoryBean.setFilterChainDefinitionMap(filterRuleMap);
    return factoryBean;
  }

  /**
   * 添加注解支持
   */
  @Bean
  @DependsOn("lifecycleBeanPostProcessor")
  public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
    DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
    advisorAutoProxyCreator.setProxyTargetClass(true);
    return advisorAutoProxyCreator;
  }

  /**
   * 添加注解依赖
   */
  @Bean
  public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
    return new LifecycleBeanPostProcessor();
  }

  /**
   * 开启注解
   */
  @Bean
  public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
    AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
    advisor.setSecurityManager(securityManager);
    return advisor;
  }
}
