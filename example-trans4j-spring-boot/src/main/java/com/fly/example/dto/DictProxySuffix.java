package com.fly.example.dto;

import com.fly.trans4j.annotation.Trans;
import com.fly.trans4j.annotation.TransVO;
import lombok.Data;

/**
 * @author 谢飞
 * @since 2023/7/12 10:11
 */
@Data
public class DictProxySuffix implements TransVO {

    @Trans(key = "sex_enum", suffix = "Desc")
    private Integer sex = 2;
}
