package com.cz.springbootswagger.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author alian
 * @date 2021/3/8 下午 2:50
 * @since JDK8
 */
@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket createSaggerApi() {
        System.out.println("-------create swagger api----------");
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Sephiroth's Springboot学习Api")
                .description("快速上手,快速开发,快速交接")
                .version("1.0.0")
                .build();
    }
}
