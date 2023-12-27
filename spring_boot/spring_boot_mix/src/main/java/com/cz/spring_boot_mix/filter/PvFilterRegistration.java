package com.cz.spring_boot_mix.filter;

import com.google.common.collect.ImmutableList;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jzm
 * @date 2023/4/26 : 10:21
 */
@Configuration
public class PvFilterRegistration {


//    @Bean
    public FilterRegistrationBean<PageViewFilter> pvFilter(){
        FilterRegistrationBean<PageViewFilter> pvFilter = new FilterRegistrationBean<>();
        pvFilter.setOrder(-1);
        pvFilter.setFilter(new PageViewFilter());
        pvFilter.setUrlPatterns(ImmutableList.of("/*"));
        return pvFilter;
    }
}
