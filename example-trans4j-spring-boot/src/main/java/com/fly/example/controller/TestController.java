package com.fly.example.controller;

import com.fly.example.dto.*;
import com.fly.example.util.R;
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
@Tag(name = "测试类")
@RestController
@RequestMapping("test")
public class TestController {

    @Operation(summary = "无翻译")
    @GetMapping("noTrans")
    public R<NoTrans> noTrans() {
        return R.success(new NoTrans());
    }

    @Operation(summary = "字典翻译（ref）")
    @GetMapping("dictRef")
    @TransMethod
    public R<DictRef> dictRef() {
        return R.success(new DictRef());
    }


    @Operation(summary = "字典翻译（ref嵌套）")
    @GetMapping("dictRefNest")
    @TransMethod
    public R<DictRefNest> dictRefNest() {
        DictRefNest dictRefNest = new DictRefNest();
        return R.success(dictRefNest);
    }

    @Operation(summary = "字典翻译（ref列表）")
    @GetMapping("dictRefList")
    @TransMethod
    public R<List<DictRef>> dictRefList() {
        List<DictRef> list = new ArrayList<>();
        list.add(new DictRef().setSex(1).setGender(2));
        list.add(new DictRef().setSex(2).setGender(1));
        return R.success(list);
    }

    @Operation(summary = "字典翻译（代理）")
    @GetMapping("dictProxy")
    @TransMethod
    public R<DictProxy> dictProxy() {
        return R.success(new DictProxy());
    }

    @Operation(summary = "字典翻译（代理列表）")
    @GetMapping("dictProxyList")
    @TransMethod
    public R<List<DictProxy>> dictProxyList() {
        List<DictProxy> list = new ArrayList<>();
        list.add(new DictProxy().setSex(1).setGender(2));
        list.add(new DictProxy().setSex(2).setGender(1));
        return R.success(list);
    }

    @Operation(summary = "枚举翻译（ref）")
    @GetMapping("enumRef")
    @TransMethod
    public R<EnumRef> enumRef() {
        return R.success(new EnumRef());
    }

    @Operation(summary = "枚举翻译（代理）")
    @GetMapping("enumProxy")
    @TransMethod
    public R<EnumProxy> enumProxy() {
        return R.success(new EnumProxy());
    }

}
