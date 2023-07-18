package com.fly.example.dto;

import com.fly.example.entity.Student;
import com.fly.trans4j.annotation.Trans;
import com.fly.trans4j.annotation.TransType;
import com.fly.trans4j.annotation.TransVO;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 谢飞
 * @since 2023/7/13 17:30
 */
@Data
public class DbRef implements TransVO {

    @Trans(type = TransType.DB, target = Student.class, refs = {"studentName", "studentCreateTime"}, fields = {"name", "createTime"})
    private Long studentId = 1L;

    private String studentName;
    private LocalDateTime studentCreateTime;
}
