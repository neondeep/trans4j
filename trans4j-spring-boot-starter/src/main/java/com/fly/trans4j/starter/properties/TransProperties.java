package com.fly.trans4j.starter.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author 谢飞
 * @since 2023/6/28 14:28
 */
@ConfigurationProperties(prefix = TransProperties.prefix)
@Getter
@Setter
public class TransProperties {
    public static final String prefix = "trans";
    /**
     * 开启全局翻译
     * 1、拦截所有responseBody进行自动翻译
     * 2、如果对于性能要求很高可关闭此配置
     */
    private Boolean enableGlobal = Boolean.FALSE;
    /**
     * 开启代理模式
     * 1、最终transMap的属性会扁平化到实体
     */
    private Boolean enableProxy = Boolean.TRUE;
}
