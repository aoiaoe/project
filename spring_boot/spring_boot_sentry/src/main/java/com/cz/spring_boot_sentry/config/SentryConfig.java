//package com.cz.spring_boot_sentry.config;
//
//import io.sentry.spring.SentryExceptionResolver;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.HandlerExceptionResolver;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//import java.util.List;
//
//@Configuration
//public class SentryConfig implements WebMvcConfigurer {
//
//    @Autowired
//    private HandlerExceptionResolver sentryExceptionResolver;
//
//    @Override
//    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
//        resolvers.add(0, sentryExceptionResolver);
//    }
//}
