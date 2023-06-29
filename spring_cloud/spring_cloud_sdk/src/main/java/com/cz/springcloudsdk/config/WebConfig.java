package com.cz.springcloudsdk.config;

import com.cz.springcloudsdk.interceptor.FeignHeaderInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

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

    /**
     * 某一些接口返回的String类型的结果，如果先封装之后再返回，则会报错
     * com.cz.springcloudsdk.response.ResultResponse cannot be cast to java.lang.String
     * 原因是因为，再类型转换器列表中，String类型的转换器排在前面，所以需要将json的序列化起放置在 列表前面
     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(0, new MappingJackson2HttpMessageConverter());
    }
}
