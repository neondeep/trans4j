package com.fly.example.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fly.example.enums.SexEnum;
import com.fly.trans4j.annotation.Trans;
import com.fly.trans4j.annotation.TransType;
import com.fly.trans4j.annotation.TransVO;
import lombok.Data;

/**
 * 混合翻译
 *
 * @author 谢飞
 * @since 2023/7/17 17:22
 */
@Data
public class Composite implements TransVO {

    // 字典代理
    @Trans(key = "sex_enum")
    private Integer dictProxySex = 1;

    // 字典ref
    @Trans(key = "sex_enum", refs = "dictRefSexName")
    private Integer dictRefSex = 2;
    private String dictRefSexName;

    // 枚举代理
    @Trans(type = TransType.ENUM, key = "desc")
    @JsonIgnore
    private SexEnum enumProxySex = SexEnum.MALE;

    // 枚举代理
    @Trans(type = TransType.ENUM, key = "desc", refs = "enumRefSexName")
    @JsonIgnore
    private SexEnum enumRefSex = SexEnum.MALE;
    private String enumRefSexName;

    // 嵌套
    private Student student = new Student();

    @Data
    public static class Student implements TransVO {

        @Trans(key = "sex_enum")
        private Integer sex = 1;
    }
}
