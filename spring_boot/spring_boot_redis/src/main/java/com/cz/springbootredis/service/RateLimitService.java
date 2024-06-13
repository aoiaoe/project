package com.cz.springbootredis.service;

import com.cz.springbootredis.service.anno.SlideWindowRateLimit;
import org.springframework.stereotype.Service;

@Service
public class RateLimitService {

    @SlideWindowRateLimit(count = 5, windowSecond = 60)
    public boolean access() {
        return true;
    }
}