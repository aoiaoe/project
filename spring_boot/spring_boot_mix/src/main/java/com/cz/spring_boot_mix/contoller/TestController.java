package com.cz.spring_boot_mix.contoller;

import com.cz.spring_boot_mix.contoller.bean.DemoBean;
import com.cz.spring_boot_mix.contoller.bean.MyBean;
import com.cz.spring_boot_mix.contoller.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
public class TestController {

    /** 线程安全
     * 此对象由AutowireUtils中的ObjectFactoryDelegatingInvocationHandler类进行代理
     */
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private TestService testService;
    @Value("${server.port:8888}")
    private String serverPort;

    @GetMapping(value = "/msg")
    public String hello(String msg){
        return request.getParameter("msg") + " from port:" + serverPort;
    }

    @GetMapping(value = "/mybean")
    public MyBean test(){
        MyBean myBean = new MyBean();
        return testService.test(myBean);
    }
    @GetMapping(value = "/demoBeans")
    public List<DemoBean> demoBeans(){
        MyBean myBean = new MyBean();
        return testService.demoBeanList();
    }

    @GetMapping(value = "/fallback")
    public String reqRemoteFallBack(){
        return this.testService.fallback();
    }

    @GetMapping(value = "/timeout")
    public String testNginxTimeout(String arg) throws InterruptedException {
        if("1".equals(arg) && "18105".equals(serverPort)){
            TimeUnit.SECONDS.sleep(2);
        }
        return arg + "from port" + serverPort;
    }
}
