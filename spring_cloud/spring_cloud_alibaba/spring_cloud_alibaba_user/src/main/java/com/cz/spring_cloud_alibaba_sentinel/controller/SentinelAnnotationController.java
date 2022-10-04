package com.cz.spring_cloud_alibaba_sentinel.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping(value = "/anno")
@RestController
public class SentinelAnnotationController {


    /**
     * '@SentinelResource 注解用于定义资源埋点，但不支持 private 方法。默认情况下，Sentinel 对控制资源的保护处理是直接抛出异常，
     * 这样对用户不友好，所以我们需要通过可选的异常处理 blockHandler 和 fallback 配置项处理一下异常信息。
     *
     * '@SentinelResource 注解包含以下属性：
     *
     * ① value： 资源名称，必需项
     *
     * ② entryType： entry 类型，可选项（默认为 EntryType.OUT）
     *
     * ③ blockHandler/blockHandlerClass： blockHandler 指定函数负责处理 BlockException 异常，可选项。blockHandler 函数默认需要和原方法在同一个类中，
     * 通过指定 blockHandlerClass 为对应类的 Class 对象，则可以指定其他类中的函数，但注意对应的函数必需为 static 函数，否则无法解析。
     *
     * blockHandler 函数访问范围需要是 public，返回类型需要与原方法相匹配，参数类型必须和原方法一致并且最后加一个类型为 BlockException 的异常参数。
     *
     * ④ fallback /fallbackClass： fallback 指定的函数负责处理业务运行的异常，可选项，fallback 函数可以针对所有类型的异常（除了exceptionsToIgnore里面排除掉的异常类型）进行处理。
     * fallback 函数默认需要和原方法在同一个类中，通过指定 fallbackClass 为对应类的 Class 对象，则可以指定指定为其他类的函数，但注意对应的函数必需为 static 函数，否则无法解析。
     *
     * fallback 函数的返回值类型必须与原函数返回值类型一致；方法参数列表需要和原函数一致，或者可以额外多一个 Throwable 类型的参数用于接收对应的异常。
     *
     * ⑤ defaultFallback（since 1.6.0）： 默认的 fallback 函数名称，可选项，通常用于通用的 fallback 逻辑。defaultFallback 函数默认需要和原方法在同一个类中，
     * 通过指定 fallbackClass 为对应类的 Class 对象，则可以指定指定为其他类的函数，但注意对应的函数必需为 static 函数，否则无法解析。
     * defaultFallback 函数可以针对所有类型的异常（除了 exceptionsToIgnore 里面排除掉的异常类型）进行处理。
     * 若同时配置了 fallback和 defaultFallback，则只有 fallback会生效。
     *
     * defaultFallback函数的返回值类型必须与原函数返回值类型一致；方法参数列表需要为空，或者可以额外多一个 Throwable 类型的参数用于接收对应的异常。
     *
     * ⑥ exceptionsToIgnore（since 1.6.0）： 用于指定哪些异常被排除掉，不会计入异常统计中，也不会进入 fallback 逻辑中，而是会原样抛出
     *
     * 特别地，若 blockHandler 和 fallback 都进行了配置，则被限流降级而抛出 BlockException 时只会进入 blockHandler 处理逻辑。
     * 若未配置 blockHandler、fallback 和 defaultFallback，则被限流降级时会将 BlockException
     * 直接抛出（若方法本身未定义 throws BlockException 则会被 JVM 包装一层 UndeclaredThrowableException）
     *
     * '@SentinelResource 的 fallback 负责业务运行的异常，blockHandler 负责 sentinel 配置的违规。
     *
     * @return
     */
    @SentinelResource(value = "anno", blockHandler = "annoBlockHandler", fallback = "annoFallback")
    @GetMapping(value = "/test1")
    public String test(){
        return "anno";
    }

    /**
     * 此方为限流降级方法，
     * '@SentinelResource的 blockHandler 属性可以配置限流降级方法,函数的返回值类型必须与原函数返回值类型一致；
     * 方法参数列表需要和原函数一致，或者可以额外多一个 BlockException 类型的参数用于接收对应的异常。
     * @param throwable
     * @return
     */
    public String annoBlockHandler(BlockException exception){
        log.error("错误:", exception);
        return "flow blocked by blockHandler method";
    }

    /**
     * 此方法为降级方法，
     * '@SentinelResource的fallback属性可以配置降级方法,函数的返回值类型必须与原函数返回值类型一致；
     * 方法参数列表需要和原函数一致，或者可以额外多一个 Throwable 类型的参数用于接收对应的异常。
     * @param throwable
     * @return
     */
    public String annoFallback(Throwable throwable){
        log.error("错误:", throwable);
        return "flow blocked by fallback method";
    }
}
