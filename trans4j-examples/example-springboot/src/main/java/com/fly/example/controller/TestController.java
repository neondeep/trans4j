package com.fly.example.controller;

import com.fly.example.dto.A001;
import com.fly.example.dto.A002;
import com.fly.example.util.R;
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


    @Operation(summary = "字典翻译a001（ref指定字段）")
    @GetMapping("a001")
    public R<A001> a001() {
        return R.success(new A001());
    }

    @Operation(summary = "字典翻译a002（代理动态生成字段）")
    @GetMapping("a002")
    public R<A002> a002() {
        return R.success(new A002());
    }
}
