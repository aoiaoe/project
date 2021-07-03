package com.cz.spring_boot_test;

import com.cz.spring_boot_test.controller.MapController;
import com.cz.spring_boot_test.service.MapService;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * 使用@WebMvcTest注解时，只有一部分的 Bean 能够被扫描得到，它们分别是：
 *
 * @Controller
 * @ControllerAdvice
 * @JsonComponent Filter
 * WebMvcConfigurer
 * HandlerMethodArgumentResolver
 * 其他常规的@Component（包括@Service、@Repository等）Bean 则不会被加载到 Spring 测试环境上下文中。
 * <p>
 * 如果测试的 MVC 控制器中需要@ComponentBean 的参与，你可以使用@MockBean注解来协助完成
 * 如果使用了mybatis的@MapperScan注解，则mapper也会自动装配，但是却么有DataSource，测试会失败，可以先注释掉@MapperScan注解
 */
@WebMvcTest(controllers = MapController.class)
public class WebMvcTestDemo {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MapService mapService;

    @Test
    public void test() throws Exception {

        Mockito.when(mapService.getValue("8")).thenReturn("String_8");
        mockMvc.perform(MockMvcRequestBuilders.get("/map/9")).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
