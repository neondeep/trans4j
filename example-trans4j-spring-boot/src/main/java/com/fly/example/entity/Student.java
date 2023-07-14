package com.fly.example.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author 谢飞
 * @since 2023/4/10 14:37
 */
@Data
@Accessors(chain = true)
public class Student {

    private Long id;

    private String name;

    @TableField(value = "is_deleted")
    private Integer deleted;

    private LocalDateTime createTime;
}
