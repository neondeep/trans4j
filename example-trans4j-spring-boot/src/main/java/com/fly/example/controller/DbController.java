package com.fly.example.controller;

import com.fly.example.dto.DbProxy;
import com.fly.example.dto.DbRef;
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
}
