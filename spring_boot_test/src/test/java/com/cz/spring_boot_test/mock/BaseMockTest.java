package com.cz.spring_boot_test.mock;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

public abstract class BaseMockTest {
    //使用 WebMvcTest 时使用
    //@autowired mockMvc 是可自动注入的。
    //当直接使用SpringBootTest 会提示 注入失败  这里直接示例利用 MockMvcBuilders工具创建
    //@Autowired
    MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wc;

    @Before
    public void beforeSetUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wc).build();
    }
}