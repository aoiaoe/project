package com.cz.spring_cloud_alibaba.controller.sentinel;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphO;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RestController
@RequestMapping(value = "/hard")
public class SentinelHardCodeController {

    /**
     * 抛出异常的方式定义资源: SphU
     *  使用 SphU.entry(“资源名”)定义资源的方式主要用于 try-catch，将需要保护的代码使用 SphU.entry("资源名") 和 entry.exit() 包围起来，当 catch 到 BlockException 时执行异常处理
     * @return
     */
    @GetMapping(value = "/sphu")
    public String code(){
        try (Entry hardcode = SphU.entry("sphu")){
            return "sphu";
        }catch (BlockException e){
            return "flow blocked";
        }
    }

    /**
     * 返回布尔值的方式定义资源: SphO
     *
     *  使用 SphO.entry(“资源名”) 定义资源的方式主要用于 if-else，当资源发生触发 sentinel 规则之后就会返回 false，这时可以根据返回值，进行限流降级的处理逻辑
     * @return
     */
    @GetMapping(value = "/spho")
    public String code2(){
        // 资源名可使用任意有业务语义的字符串
        if (SphO.entry("spho")) {
            // 务必保证finally会被执行
            try {
                return "spho";
            } finally {
                SphO.exit();
            }
        } else {
            return "flow blocked";
        }
    }
}
