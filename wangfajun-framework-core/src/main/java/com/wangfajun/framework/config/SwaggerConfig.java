package com.wangfajun.framework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger2接口文档，只在dev和test环境下才会进行组件的实例化和注入
 * http://localhost:8082/flexible/swagger-ui.html
 * @author : wangfajun
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.zhundian.flexible.controller"))
                .paths(PathSelectors.any())
                .build().apiInfo(new ApiInfoBuilder()
                        .title("灵活用工接口文档")
                        .description("灵活用工接口文档")
                        .version("9.0")
                        .contact(new Contact("zhundian", "http://www.zhundian.cn", "zhengyangjun@zhundian.cn"))
                        .license("上海准点企业服务有限公司")
                        .licenseUrl("http://www.zhundian.cn")
                        .build());
    }
}