package com.fly.example;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.fly.example.dto.*;
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
public class Trans4jTest {

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
    }


    @Test
    @Order(101)
    @DisplayName("字典翻译（ref）")
    public void dictRefTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/dict/dictRef"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.content()
                .json(
                    wrapperR(
                        new DictRef().setSex(1).setSexName("男").setGender(2).setGenderName("女")
                    )
                    , true)
            );
    }

    @Test
    @Order(102)
    @DisplayName("字典翻译（ref列表）")
    public void dictRefListTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/dict/dictRefList"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.content()
                .json(
                    wrapperR(
                        Arrays.asList(
                            new DictRef().setSex(1).setSexName("男").setGender(2).setGenderName("女"),
                            new DictRef().setSex(2).setSexName("女").setGender(1).setGenderName("男")
                        )
                    )
                    , true)
            );
    }

    @Test
    @Order(103)
    @DisplayName("字典翻译（ref嵌套）")
    public void dictRefNestTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/dict/dictRefNest"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.content()
                .json(
                    wrapperR(
                        new DictRefNest().setSex(1).setSexName("男").setGender(2).setGenderName("女")
                            .setStudent(new DictRefNest.Student().setSex(1).setSexName("男"))
                    )
                    , true)
            );
    }


    @Test
    @Order(103)
    @DisplayName("字典翻译（ref嵌套列表）")
    public void dictRefNestList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/dict/dictRefNestList"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.content()
                .json(
                    wrapperR(
                        new DictRefNestList().setSex(1).setSexName("男").setGender(2).setGenderName("女")
                            .setStudent(new DictRefNestList.Student().setSex(1).setSexName("男"))
                            .setList(Arrays.asList(
                                    new DictRefNestList.Student().setSex(1).setSexName("男"),
                                    new DictRefNestList.Student().setSex(2).setSexName("女"),
                                    new DictRefNestList.Student().setSex(0).setSexName("未知"),
                                    new DictRefNestList.Student().setSex(2).setSexName("女")
                                )
                            )
                    )
                    , true)
            );
    }

    @Test
    @Order(104)
    @DisplayName("字典翻译（代理）")
    public void dictProxyTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/dict/dictProxy"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.content()
                .json(
                    wrapperR(
                        new JSONObject().set("sex", 1).set("sexName", "男").set("gender", 2).set("genderName", "女")
                    )
                    , true)
            );
    }


    @Test
    @Order(105)
    @DisplayName("字典翻译（代理列表）")
    public void dictProxyListTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/dict/dictProxyList"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.content()
                .json(
                    wrapperR(
                        Arrays.asList(
                            new JSONObject().set("sex", 1).set("sexName", "男").set("gender", 2).set("genderName", "女"),
                            new JSONObject().set("sex", 2).set("sexName", "女").set("gender", 1).set("genderName", "男")
                        )
                    )
                    , true)
            );
    }

    @Test
    @Order(106)
    @DisplayName("字典翻译（代理嵌套）")
    public void dictProxyNestTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/dict/dictProxyNest"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.content()
                .json(wrapperR(
                        new JSONObject()
                            .set("sex", 1).set("sexName", "男")
                            .set("gender", 2).set("genderName", "女")
                            .set("teacher", new JSONObject().set("sex", 2).set("sexName", "女")
                                .set("student", new JSONObject().set("sex", 1).set("sexName", "男")))
                    )
                    , true)
            );
    }

    @Test
    @Order(106)
    @DisplayName("字典翻译（代理嵌套列表）")
    public void dictProxyNestList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/dict/dictProxyNestList"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.content()
                .json(wrapperR(
                        new JSONObject()
                            .set("sex", 1).set("sexName", "男")
                            .set("gender", 2).set("genderName", "女")
                            .set("teacher", new JSONObject()
                                .set("sex", 2).set("sexName", "女")
                                .set("student", new JSONObject().set("sex", 1).set("sexName", "男"))
                                .set("studentList", Arrays.asList(
                                    new JSONObject().set("sex", 1).set("sexName", "男"),
                                    new JSONObject().set("sex", 2).set("sexName", "女"),
                                    new JSONObject().set("sex", 0).set("sexName", "未知"),
                                    new JSONObject().set("sex", 2).set("sexName", "女")
                                )))
                    )
                    , true)
            );
    }

    @Test
    @Order(107)
    @DisplayName("字典翻译（ref json 忽略）")
    public void dictRefJsonIgnore() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/dict/dictRefJsonIgnore"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.content()
                .json(wrapperR(
                        new JSONObject().set("sexName", "男").set("genderName", "女")
                    )
                    , true)
            );
    }

    @Test
    @Order(107)
    @DisplayName("字典翻译（代理自定义字段后缀）")
    public void dictProxySuffix() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/dict/dictProxySuffix"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.content()
                .json(wrapperR(
                        new JSONObject().set("sex", 2).set("sexDesc", "女")
                    )
                    , true)
            );
    }


    @Test
    @Order(201)
    @DisplayName("枚举翻译（ref）")
    public void enumRefTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/enum/enumRef"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.content()
                .json(
                    wrapperR(
                        new EnumRef().setSex(SexEnum.MALE).setSexEnumNameRef("男")
                    )
                    , true)
            );
    }

    @Test
    @Order(202)
    @DisplayName("枚举翻译（代理）")
    public void enumProxyTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/enum/enumProxy"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.content()
                .json(
                    wrapperR(
                        new JSONObject().set("sex", "MALE").set("sexName", "男")
                    )
                    , true)
            );
    }

    @Test
    @Order(301)
    @DisplayName("数据库翻译（ref）")
    public void dbRefTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/db/dbRef"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.content()
                .json(
                    wrapperR(
                        new JSONObject().set("studentId", 1).set("studentName", "张三").set("studentCreateTime", "2023-07-17 12:12:12")
                    )
                    , true)
            );
    }

    @Test
    @Order(301)
    @DisplayName("数据库翻译（代理）")
    public void dbProxyTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/db/dbProxy"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.content()
                .json(
                    wrapperR(
                        new JSONObject().set("studentId", 1).set("name", "张三").set("createTime", "2023-07-17T12:12:12")
                    )
                    , true)
            );
    }

    @Test
    @Order(301)
    @DisplayName("数据库翻译（数组）")
    public void dbArrayTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/db/dbArray"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.content()
                .json(
                    wrapperR(
                        new JSONObject()
                            .set("id1List", Arrays.asList(1, 2, 3))
                            .set("name", "张三,李四,王五")
                            .set("createTime", "2023-07-17T12:12:12,2023-07-17T12:12:12,2023-07-17T12:12:12")
                            .set("id2List", Arrays.asList(2, 3))
                            .set("name2", "李四,王五")
                            .set("createTime2", "2023-07-17T12:12:12,2023-07-17T12:12:12")
                    )
                    , true)
            );
    }

    @Test
    @Order(301)
    @DisplayName("db翻译（逗号分割的字符串）")
    public void dbStrCommaTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/db/dbStrComma"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.content()
                .json(
                    wrapperR(
                        new JSONObject()
                            .set("id1Str", "1,2,3")
                            .set("name", "张三,李四,王五")
                            .set("createTime", "2023-07-17T12:12:12,2023-07-17T12:12:12,2023-07-17T12:12:12")
                            .set("id2Str", "2,3")
                            .set("name2", "李四,王五")
                            .set("createTime2", "2023-07-17T12:12:12,2023-07-17T12:12:12")
                    )
                    , true)
            );
    }

    @Test
    @Order(301)
    @DisplayName("混合翻译")
    public void compositeTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/test/composite"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.content()
                .json(
                    wrapperR(
                        new JSONObject()
                            .set("dictProxySex", 1).set("dictProxySexName", "男")
                            .set("dictRefSex", 2).set("dictRefSexName", "女")
                            .set("enumProxySexName", "男")
                            .set("enumRefSexName", "男")
                            .set("student", new JSONObject().set("sex", 1).set("sexName", "男"))
                    )
                    , true)
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

