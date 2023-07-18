package com.fly.example.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fly.trans4j.annotation.Trans;
import com.fly.trans4j.annotation.TransVO;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author 谢飞
 * @since 2023/6/26 17:46
 */
@Data
@Accessors(chain = true)
public class DictJsonIgnore implements TransVO {

    @JsonIgnore
    @Trans(key = "sex_enum", refs = "sexName")
    private Integer sex = 1;
    private String sexName;

    @JsonIgnore
    @Trans(key = "sex_enum")
    private Integer gender = 2;
}
