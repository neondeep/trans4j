package com.fly.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 谢飞
 * @since 2023/6/9 14:27
 */
@Tag(name = "测试类")
@RestController
@RequestMapping("test")
public class TestController {

    @Operation(summary = "测试")
    @GetMapping("test1")
    public Object test1() {

        return "success";
    }
}
