package com.fly.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fly.example.entity.Student;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 谢飞
 * @since 2023/4/10 14:37
 */
@Mapper
public interface StudentMapper extends BaseMapper<Student> {
}
