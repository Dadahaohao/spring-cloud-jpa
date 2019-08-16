package com.bwei.haohao.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2Configuration {

    @Bean
    public Docket accessToken() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("api")// 定义组
                .select() // 选择那些路径和api会生成document
                .apis(RequestHandlerSelectors.basePackage("com.bawei.user.controller")) // 拦截的包路径
                .paths(PathSelectors.regex("/test/.*"))// 拦截的接口路径
                .build() // 创建
                .apiInfo(apiInfo()); // 配置说明
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()//
                .title("八维教育")// 标题
                .description("spring boot swagger整合")// 描述
                //.termsOfServiceUrl("http://www.bawei.com")//
                //.contact(new Contact("jianganming", "http://www.bawei.com", "867988604@qq.com"))// 联系
                .version("1.0")// 版本
                .build();
    }

}
