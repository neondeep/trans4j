package com.fly.trans4j.autoconfigure;

import com.fly.trans4j.core.BodyTrans;
import com.fly.trans4j.core.ResponseBodyAdviceTrans;
import com.fly.trans4j.core.trans.AbstractTransService;
import com.fly.trans4j.core.trans.DictTransService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author 谢飞
 * @since 2023/6/9 11:25
 */
@Configuration
public class Trans4jAutoConfiguration {

//    @Bean
//    public BodyTrans bodyTrans(){
//        return new BodyTrans();
//    }

    @Bean
    public ResponseBodyAdviceTrans responseBodyAdviceTrans(){
        return new ResponseBodyAdviceTrans();
    }

    @Bean
    public DictTransService dictTransService(){
        return new DictTransService();
    }

}
