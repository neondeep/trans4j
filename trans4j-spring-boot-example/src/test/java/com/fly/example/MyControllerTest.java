package com.fly.example;

import cn.hutool.json.JSONUtil;
import com.fly.example.dto.A001;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * @author 谢飞
 * @since 2023/6/26 17:38
 */
@SpringBootTest
@AutoConfigureMockMvc
public class MyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetMyData() throws Exception {
        // 发起 GET 请求，并验证返回的状态码和内容
        mockMvc.perform(MockMvcRequestBuilders.get("/test/a001"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(System.out::println)
            .andExpect(MockMvcResultMatchers.content().json(JSONUtil.toJsonStr(new A001().setSex(1).setSexName("男"))));
    }
}

