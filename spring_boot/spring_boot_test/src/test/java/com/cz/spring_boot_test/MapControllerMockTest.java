package com.cz.spring_boot_test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * 集成测试.不启动tomcat环境
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootTestApplication.class)
@AutoConfigureMockMvc
public class MapControllerMockTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void test() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/map/key/9")).andDo(MockMvcResultHandlers.print())
                // 断言
                .andExpect(MockMvcResultMatchers.status().isOk())
                // 打印请求响应信息。
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void test1() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/map/json")
                .header("token", "abc")
                .param("id", "111")
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("token").value("abc"))
                .andReturn();
    }


}
