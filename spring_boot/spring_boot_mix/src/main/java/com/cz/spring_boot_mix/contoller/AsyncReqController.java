package com.cz.spring_boot_mix.contoller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.AsyncContext;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@RequestMapping(value = "/async")
@RestController
public class AsyncReqController {

    static int x = 0;
    static int y = 0;

    ExecutorService executor = Executors.newSingleThreadExecutor();

    @GetMapping(value = "/syncGet")
    public int syncGet(){

        return x++;
    }

    @GetMapping(value = "/asyncGet")
    public void asyncGet(HttpServletRequest request){
        AsyncContext asyncContext = request.startAsync();
        asyncContext.setTimeout(5000);
        executor.execute(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
                y++;
                HttpServletResponse response = (HttpServletResponse) asyncContext.getResponse();
                response.setStatus(HttpServletResponse.SC_OK);
                response.setHeader("Content-Type", "application/json");
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("res", y);
                response.getWriter().println(jsonObject.toJSONString());
                asyncContext.complete();
            }catch (Exception e){
                e.printStackTrace();
            }
        });
    }
}
