package com.fly.example;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.fly.example.dto.DictRef;
import com.fly.example.dto.DictRefNest;
import com.fly.example.dto.EnumRef;
import com.fly.example.dto.NoTrans;
import com.fly.example.enums.SexEnum;
import com.fly.example.util.R;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

/**
 * @author 谢飞
 * @since 2023/6/26 17:38
 */
@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
public class TestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(0)
    @DisplayName("无翻译")
    public void noTransTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/test/noTrans"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.content()
                .json(wrapperR(new NoTrans())))
            .andReturn();
        System.out.println(mvcResult);
    }

    @Test
    @Order(101)
    @DisplayName("字典翻译（ref）")
    public void dictRefTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/test/dictRef"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.content()
                .json(wrapperR(
                        new DictRef().setSex(1).setSexName("男").setGender(2).setGenderName("女")
                    )
                )
            );
    }

    @Test
    @Order(102)
    @DisplayName("字典翻译（ref列表）")
    public void dictRefListTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/test/dictRefList"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.content()
                .json(wrapperR(
                        Arrays.asList(
                            new DictRef().setSex(1).setSexName("男").setGender(2).setGenderName("女"),
                            new DictRef().setSex(2).setSexName("女").setGender(1).setGenderName("男")
                        )
                    )
                )
            );
    }

    @Test
    @Order(103)
    @DisplayName("字典翻译（ref嵌套）")
    public void dictRefNestTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/test/dictRefNest"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.content()
                .json(wrapperR(
                        new DictRefNest().setSex(1).setSexName("男").setGender(2).setGenderName("女").setStudent(new DictRefNest.Student().setSex(1).setSexName("男"))
                    )
                )
            );
    }


    @Test
    @Order(104)
    @DisplayName("字典翻译（代理）")
    public void dictProxyTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/test/dictProxy"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.content()
                .json(wrapperR(
                        new JSONObject().set("sex", 1).set("sexName", "男").set("gender", 2).set("genderName", "女")
                    )
                )
            );
    }

    @Test
    @Order(105)
    @DisplayName("字典翻译（代理列表）")
    public void dictProxyListTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/test/dictProxyList"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.content()
                .json(wrapperR(
                        Arrays.asList(
                            new JSONObject().set("sex", 1).set("sexName", "男").set("gender", 2).set("genderName", "女"),
                            new JSONObject().set("sex", 2).set("sexName", "女").set("gender", 1).set("genderName", "男")
                        )
                    )
                )
            );
    }


    @Test
    @Order(201)
    @DisplayName("枚举翻译（ref）")
    public void enumRefTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/test/enumRef"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.content()
                .json(wrapperR(
                        new EnumRef().setSex(SexEnum.MALE).setSexEnumNameRef("男")
                    )
                )
            );
    }

    @Test
    @Order(204)
    @DisplayName("枚举翻译（代理）")
    public void enumProxyTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/test/enumProxy"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.content()
                .json(wrapperR(
                        new JSONObject().set("sex", "MALE").set("sexName", "男")
                    )
                )
            );
    }

    /**
     * 包裹R后的json数据
     */
    public String wrapperR(Object obj) {
        R<Object> r = R.success(obj);
        String jsonStr = JSONUtil.toJsonStr(r);
        log.info("{}", jsonStr);
        return jsonStr;
    }
}

