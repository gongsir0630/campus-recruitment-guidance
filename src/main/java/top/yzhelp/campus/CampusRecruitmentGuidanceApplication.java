package top.yzhelp.campus;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;

/**
 * @author 码之泪殇 <gongsir0630@gmail.com>
 * Created on 2022-02-12
 */
@SpringBootApplication
public class CampusRecruitmentGuidanceApplication {
  public static void main(String[] args) {
    SpringApplication.run(CampusRecruitmentGuidanceApplication.class, args);
  }

  /**
   * fastjson 配置注入: 使用阿里巴巴的 fastjson 处理 json 信息
   * @return HttpMessageConverters
   */
  @Bean
  public HttpMessageConverters fastJsonMessageConverters() {
    // fastjson 消息转换对象
    FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
    // fastjson 配置
    FastJsonConfig config = new FastJsonConfig();
    config.setSerializerFeatures(SerializerFeature.PrettyFormat);
    config.setDateFormat("yyyy-MM-dd");
    // 配置注入消息转换器
    converter.setFastJsonConfig(config);
    // 让 spring 使用自定义的消息转换器
    return new HttpMessageConverters(converter);
  }
}
