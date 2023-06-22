package com.fly.example.dto;

import com.fly.trans4j.annotation.Trans;
import com.fly.trans4j.annotation.TransVO;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author 谢飞
 * @since 2023/6/9 17:24
 */
@Data
@Accessors(chain = true)
public class Student implements TransVO {

    private Integer id;

    @Trans(key = "sex_enum")
    private Integer sex;

//    private String sexName;
}
