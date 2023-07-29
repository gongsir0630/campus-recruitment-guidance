package top.yzhelp.campus.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author kyle <gongsir0630@gmail.com>
 * Created on 2022-02-12
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${spring.application.name}")
    private String appName;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo())
            .select()
            .apis(RequestHandlerSelectors.basePackage("top.yzhelp.campus.controller"))
            .paths(PathSelectors.any())
            .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title(appName)
            .description("基于 Swagger 构建的 Rest API 文档, 问题反馈请联系开发者kyle")
            .contact(new Contact("kyle", "https://github.com/gongsir0630", "gongsir0630@gmail.com"))
            .termsOfServiceUrl("https://github.com/gongsir0630/campus-recruitment-guidance")
            .version("1.0")
            .build();
    }
}

