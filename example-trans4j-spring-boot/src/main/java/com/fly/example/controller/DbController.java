package com.fly.example.controller;

import com.fly.example.dto.DbArray;
import com.fly.example.dto.DbProxy;
import com.fly.example.dto.DbRef;
import com.fly.example.dto.DbStrComma;
import com.fly.example.util.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 谢飞
 * @since 2023/7/13 17:29
 */
@Tag(name = "数据库翻译")
@RestController
@RequestMapping("db")
public class DbController {

    @Operation(summary = "db翻译（ref）")
    @GetMapping("dbRef")
    public R<DbRef> dbRef() {
        return R.success(new DbRef());
    }

    @Operation(summary = "db翻译（代理）")
    @GetMapping("dbProxy")
    public R<DbProxy> dbProxy() {
        return R.success(new DbProxy());
    }

    @Operation(summary = "db翻译（数组）")
    @GetMapping("dbArray")
    public R<DbArray> dbArray() {
        return R.success(new DbArray());
    }

    @Operation(summary = "db翻译（逗号分割的字符串）")
    @GetMapping("dbStrComma")
    public R<DbStrComma> dbStrComma() {
        return R.success(new DbStrComma());
    }
}
