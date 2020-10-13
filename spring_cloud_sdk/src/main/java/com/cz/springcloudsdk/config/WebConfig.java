package com.cz.springcloudsdk.config;

import com.cz.springcloudsdk.interceptor.FeignHeaderInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author alian
 * @date 2020/10/13 下午 3:19
 * @since JDK8
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new FeignHeaderInterceptor())
                .addPathPatterns("/**");
    }
}
