package com.cz.spring_boot_mix.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author jzm
 * @date 2023/4/26 : 10:15
 */
@Slf4j
public class PageViewFilter implements Filter {

    private AtomicInteger count;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("初始化PV过滤器");
        count = new AtomicInteger();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        int i = count.incrementAndGet();
        log.info("第{}个访问请求", i);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        count = null;
        log.info("销毁PV过滤器");
    }
}
