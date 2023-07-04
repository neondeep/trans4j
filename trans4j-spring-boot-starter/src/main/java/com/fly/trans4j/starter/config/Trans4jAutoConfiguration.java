package com.fly.trans4j.starter.config;

import com.fly.trans4j.advice.ResponseBodyAdviceTrans;
import com.fly.trans4j.aspectj.TransMethodAspect;
import com.fly.trans4j.core.ResponseFilter;
import com.fly.trans4j.core.TransConfiguration;
import com.fly.trans4j.starter.properties.TransProperties;
import com.fly.trans4j.trans.DictTransService;
import com.fly.trans4j.trans.EnumTransService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 谢飞
 * @since 2023/6/9 11:25
 */
@EnableConfigurationProperties(TransProperties.class)
@Configuration
public class Trans4jAutoConfiguration implements InitializingBean {

    private final TransProperties transProperties;

    public Trans4jAutoConfiguration(TransProperties transProperties) {
        this.transProperties = transProperties;
    }

    @Bean
    @ConditionalOnProperty(prefix = TransProperties.prefix, name = "enable-global", havingValue = "true")
    public ResponseBodyAdviceTrans responseBodyAdviceTrans() {
        return new ResponseBodyAdviceTrans();
    }

    @Bean
    @ConditionalOnMissingBean(ResponseBodyAdviceTrans.class)
    public TransMethodAspect transMethodAspect() {
        return new TransMethodAspect();
    }

    @Bean
    public ResponseFilter responseFilter() {
        return new ResponseFilter();
    }

    @Bean
    public DictTransService dictTransService() {
        return new DictTransService();
    }

    @Bean
    public EnumTransService enumTransService() {
        return new EnumTransService();
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        TransConfiguration configuration = TransConfiguration.getInstance();
        BeanUtils.copyProperties(transProperties, configuration);
    }
}
