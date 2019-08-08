package com.ruxuanwo.data.export;

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
 * @author Created by ruxuanwo on 2018/02/06
 */
@Configuration
@EnableSwagger2
public class Swagger {
    /**
     *basePackage对应的是controller层所在的包路径，及web包所在的路径
     */
    @Bean
    public Docket creatRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ruxuanwo.data.export.web"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * API访问路径：/swagger-ui.html
     * title: 模块名称
     * description: 模块描述
     * termsOfServiceUrl:条款地址(不可见)
     * Contact：开发者姓名、urll、开发者邮箱
     * version:版本
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("title")
                .description("description")
                .termsOfServiceUrl("http://IP:Port/swagger-ui.html")
                .contact(new Contact("ruxuanwo","http://www.ruxuanwo.cn","ruxuanwo@163.com"))
                .version("3.0.0")
                .build();
    }
}
