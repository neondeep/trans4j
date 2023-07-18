package com.fly.example.controller;

import com.fly.example.dto.*;
import com.fly.example.util.R;
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
@Tag(name = "字典翻译")
@RestController
@RequestMapping("dict")
public class DictController {

    @Operation(summary = "字典翻译（ref）")
    @GetMapping("dictRef")
    public R<DictRef> dictRef() {
        return R.success(new DictRef());
    }

    @Operation(summary = "字典翻译（ref嵌套）")
    @GetMapping("dictRefNest")
    public R<DictRefNest> dictRefNest() {
        return R.success(new DictRefNest());
    }

    @Operation(summary = "字典翻译（ref嵌套列表）")
    @GetMapping("dictRefNestList")
    public R<DictRefNestList> dictRefNestList() {
        DictRefNestList vo = new DictRefNestList();
        List<DictRefNestList.Student> studentList = new ArrayList<>();
        studentList.add(new DictRefNestList.Student().setSex(1));
        studentList.add(new DictRefNestList.Student().setSex(2));
        studentList.add(new DictRefNestList.Student().setSex(0));
        studentList.add(new DictRefNestList.Student().setSex(2));
        vo.setList(studentList);
        return R.success(vo);
    }


    @Operation(summary = "字典翻译（ref列表）")
    @GetMapping("dictRefList")
    public R<List<DictRef>> dictRefList() {
        List<DictRef> list = new ArrayList<>();
        list.add(new DictRef().setSex(1).setGender(2));
        list.add(new DictRef().setSex(2).setGender(1));
        return R.success(list);
    }


    @Operation(summary = "字典翻译（代理）")
    @GetMapping("dictProxy")
    public R<DictProxy> dictProxy() {
        return R.success(new DictProxy());
    }

    @Operation(summary = "字典翻译（代理列表）")
    @GetMapping("dictProxyList")
    public R<List<DictProxy>> dictProxyList() {
        List<DictProxy> list = new ArrayList<>();
        list.add(new DictProxy().setSex(1).setGender(2));
        list.add(new DictProxy().setSex(2).setGender(1));
        return R.success(list);
    }

    @Operation(summary = "字典翻译（代理嵌套）")
    @GetMapping("dictProxyNest")
    public R<DictProxyNest> dictProxyNest() {
        return R.success(new DictProxyNest());
    }

    @Operation(summary = "字典翻译（代理嵌套列表）")
    @GetMapping("dictProxyNestList")
    public R<DictProxyNestList> dictProxyNestList() {
        DictProxyNestList vo = new DictProxyNestList();
        List<DictProxyNestList.Student> studentList = new ArrayList<>();
        studentList.add(new DictProxyNestList.Student().setSex(1));
        studentList.add(new DictProxyNestList.Student().setSex(2));
        studentList.add(new DictProxyNestList.Student().setSex(0));
        studentList.add(new DictProxyNestList.Student().setSex(2));
        vo.getTeacher().setStudentList(studentList);
        return R.success(vo);
    }


    @Operation(summary = "字典翻译（ref json 忽略）")
    @GetMapping("dictRefJsonIgnore")
    public R<DictJsonIgnore> dictRefJsonIgnore() {
        return R.success(new DictJsonIgnore());
    }

    @Operation(summary = "字典翻译（代理自定义字段后缀）")
    @GetMapping("dictProxySuffix")
    public R<DictProxySuffix> dictProxySuffix() {
        return R.success(new DictProxySuffix());
    }

}
