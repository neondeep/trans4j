package com.fly.trans4j.autoconfigure;

import com.fly.trans4j.core.AutoTrans;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 谢飞
 * @since 2023/6/9 11:25
 */
@Configuration
public class Trans4jAutoConfiguration {

    @Bean
    public AutoTrans autoTrans(){
        return new AutoTrans();
    }
}
