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
public class A001 implements TransVO {
    @Trans(key = "sex_enum", ref = "sexName")
    private Integer sex = 1;

    @Trans(key = "sex_enum", ref = "genderName")
    private Integer gender = 2;

    private String sexName;
    private String genderName;
}
