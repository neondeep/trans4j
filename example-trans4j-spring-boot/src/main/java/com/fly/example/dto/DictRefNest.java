package com.fly.example.dto;

import com.fly.trans4j.annotation.Trans;
import com.fly.trans4j.annotation.TransVO;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 实现sex翻译到sexName
 *
 * @author 谢飞
 * @since 2023/6/26 17:46
 */
@Data
@Accessors(chain = true)
public class DictRefNest implements TransVO {
    @Trans(key = "sex_enum", refs = "sexName")
    private Integer sex = 1;
    private String sexName;

    @Trans(key = "sex_enum", refs = "genderName")
    private Integer gender = 2;
    private String genderName;

    private Student student = new Student();

    @Data
    @Accessors(chain = true)
    public static class Student implements TransVO{
        @Trans(key = "sex_enum", refs = "sexName")
        private Integer sex = 1;
        private String sexName;
    }
}
