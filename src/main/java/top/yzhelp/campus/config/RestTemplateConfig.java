package top.yzhelp.campus.config;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import top.yzhelp.campus.constant.CrgMagicNumber;

/**
 * @author kyle <gongsir0630@gmail.com>
 * Created on 2022-02-12
 */
@Configuration
public class RestTemplateConfig {

    /**
     * 默认连接/读取超时时间
     */
    private static final long CONN_TIMEOUT = Duration.ofSeconds(CrgMagicNumber.TEN).toMillis();
    private static final long READ_TIMEOUT = Duration.ofMinutes(CrgMagicNumber.ONE).toMillis();

    @Bean
    public ClientHttpRequestFactory simpleClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        // 连接超时时间设置为10秒
        factory.setConnectTimeout(Math.toIntExact(CONN_TIMEOUT));
        // 读取超时时间为单位为60秒
        factory.setReadTimeout(Math.toIntExact(READ_TIMEOUT));
        return factory;
    }

    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory factory) {
        return new RestTemplate(factory);
    }
}
