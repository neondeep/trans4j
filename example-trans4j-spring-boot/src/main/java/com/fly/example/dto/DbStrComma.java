package com.fly.example.dto;

import com.fly.example.entity.Student;
import com.fly.trans4j.annotation.Trans;
import com.fly.trans4j.annotation.TransType;
import com.fly.trans4j.annotation.TransVO;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

/**
 * @author 谢飞
 * @since 2023/7/13 17:30
 */
@Data
public class DbStrComma implements TransVO {

    // 代理
    @Trans(type = TransType.DB, target = Student.class, fields = {"name", "createTime"})
    private String id1Str = "1,2,3";

    // ref
    @Trans(type = TransType.DB, target = Student.class, fields = {"name", "createTime"}, refs = {"name2", "createTime2"})
    private String id2Str = "2,3";
    private String name2;
    private String createTime2;
}
