package com.cz.spring_boot_mix.contoller;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 测试客户端/浏览器缓存
 */
@Controller
public class HttpClientCacheController {

    private Cache<String, Long> cache = CacheBuilder.newBuilder().expireAfterWrite(10, TimeUnit.SECONDS).build();


    @GetMapping(value = "/cacheTest")
    public ResponseEntity<String> cache(@RequestHeader(value = "If-Modified-Since", required = false) Date ifModifiedSince) throws ExecutionException {
//        DateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss 'GMT'", Locale.US);
        DateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US);

//        Fri, 22 Dec 2023 08:29:43 GMT
//        Fri, 22 Dec 2023 16:29:02 GMT

        // 上一次修改时间
        Long lastModified = getLastModified() / 1000 * 1000;
        Long now = System.currentTimeMillis() /1000 * 1000;

        Long cacheMaxAge = 20L;
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("Date", dateFormat.format(new Date(now)));
        headers.add("Expires", dateFormat.format(new Date(now + cacheMaxAge * 1000)));
        headers.add("Cache-Control", "max-age=" + cacheMaxAge);
        // 时间相同，说明内容未作出修改
        if(ifModifiedSince != null && ifModifiedSince.getTime() == lastModified){

            return new ResponseEntity<String>(headers, HttpStatus.NOT_MODIFIED);
        }
        headers.add("Last-Modified", dateFormat.format(new Date(lastModified)));
        String body = "<a href=''>点击访问当前链接</a>";
        return new ResponseEntity<String>(body, headers, HttpStatus.OK);
    }

    public Long getLastModified() throws ExecutionException {
        return cache.get("lastModified", () -> {return System.currentTimeMillis();});
    }
}
