package com.fly.example.controller;

import com.fly.example.dto.Student;
import com.fly.example.util.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

/**
 * @author 谢飞
 * @since 2023/6/9 14:27
 */
@Tag(name = "测试类")
@RestController
@RequestMapping("test")
public class TestController {


    @Operation(summary = "返回字符串")
    @GetMapping("returnString")
    public String returnString() {
        return "success";
    }

    @Operation(summary = "返回对象")
    @GetMapping("returnObj")
    public Student returnObj() {
        return new Student().setId(1).setSex(1);
    }

    @Operation(summary = "返回对象列表")
    @GetMapping("returnListObj")
    public List<Student> returnListObj() {
        return Collections.singletonList(new Student().setId(1).setSex(1));
    }

    @Operation(summary = "返回R对象")
    @GetMapping("returnRObj")
    public R<Student> returnRObj() {
        return R.success(new Student().setId(1).setSex(1));
    }

    @Operation(summary = "返回R对象列表")
    @GetMapping("returnRListObj")
    public R<List<Student>> returnRListObj() {
        return R.success(Collections.singletonList(new Student().setId(1).setSex(1)));
    }
}
