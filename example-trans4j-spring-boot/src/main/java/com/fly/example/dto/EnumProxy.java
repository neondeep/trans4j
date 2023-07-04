package com.fly.example.dto;

import com.fly.example.enums.SexEnum;
import com.fly.trans4j.annotation.Trans;
import com.fly.trans4j.annotation.TransType;
import com.fly.trans4j.annotation.TransVO;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author 谢飞
 * @since 2023/7/4 15:12
 */
@Data
@Accessors(chain = true)
public class EnumProxy implements TransVO {

    @Trans(type = TransType.ENUM, key = "desc")
    private SexEnum sex = SexEnum.MALE;
}
