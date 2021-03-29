package top.yzhelp.campus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @author <a href="https://github.com/gongsir0630">码之泪殇</a>
 * @date 2021/3/29 12:23
 * 你的指尖,拥有改变世界的力量
 * @description RestTemplate的配置类
 */
@Configuration
public class RestTemplateConfig {
  @Bean
  public RestTemplate restTemplate(ClientHttpRequestFactory factory) {
    return new RestTemplate(factory);
  }

  @Bean
  public ClientHttpRequestFactory simpleClientHttpRequestFactory() {
    SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
    // 连接超时时间设置为10秒
    factory.setConnectTimeout(1000 * 10);
    // 读取超时时间为单位为60秒
    factory.setReadTimeout(1000 * 60);
    return factory;
  }

}
