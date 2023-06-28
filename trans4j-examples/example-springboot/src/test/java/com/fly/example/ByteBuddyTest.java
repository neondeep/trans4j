package com.fly.example;

import cn.hutool.core.util.ReflectUtil;
import lombok.Data;
import lombok.experimental.Accessors;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Modifier;

/**
 * @author 谢飞
 * @since 2023/6/25 16:54
 */
public class ByteBuddyTest {

    public static class StudentProxyExample {
        public static void main(String[] args) throws Exception {
            Student student = new Student().setName("张三");
            Class<?> originalClass = student.getClass();

            DynamicType.Unloaded<?> unloadedType = new ByteBuddy()
                .subclass(originalClass)
                .name(originalClass.getSimpleName() + "DynamicTypeBuilder")
                .defineField("age", Integer.class, Modifier.PRIVATE)
                .make();

            Class<?> dynamicClass = unloadedType.load(originalClass.getClassLoader(), ClassLoadingStrategy.Default.INJECTION).getLoaded();
            Object newObj = dynamicClass.newInstance();

            // 将原始对象的字段值复制到子类对象
            BeanUtils.copyProperties(student, newObj);
            ReflectUtil.setFieldValue(newObj, "age", 12);
            // 打印字段，或者debug看
            System.out.println(ReflectUtil.getFieldValue(newObj, "age"));
        }

        @Data
        @Accessors(chain = true)
        public static class Student {
            private String name;
        }
    }
}
