package com.fly.example.config;

import com.fly.trans4j.core.trans.DictTransService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 谢飞
 * @since 2023/6/12 15:54
 */
@Configuration
public class TransConfig implements ApplicationRunner {

    @Autowired
    private DictTransService dictTransService;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        Map<String, String> transMap = new HashMap<>();
        transMap.put("0", "未知");
        transMap.put("1", "男");
        transMap.put("2", "女");
        dictTransService.refreshCache("sex_enum", transMap);
    }
}
