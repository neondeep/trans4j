package com.fly.example.controller;

import com.fly.example.dto.*;
import com.fly.example.util.R;
import com.fly.trans4j.annotation.IgnoreTrans;
import com.fly.trans4j.annotation.TransMethod;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 谢飞
 * @since 2023/6/9 14:27
 */
@Tag(name = "枚举翻译")
@RestController
@RequestMapping("enum")
public class EnumController {

    @Operation(summary = "枚举翻译（ref）")
    @GetMapping("enumRef")
    public R<EnumRef> enumRef() {
        return R.success(new EnumRef());
    }

    @Operation(summary = "枚举翻译（代理）")
    @GetMapping("enumProxy")
    public R<EnumProxy> enumProxy() {
        return R.success(new EnumProxy());
    }

}
