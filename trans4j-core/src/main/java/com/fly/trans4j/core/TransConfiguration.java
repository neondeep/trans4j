package com.fly.trans4j.core;

import lombok.Data;

/**
 * @author 谢飞
 * @since 2023/6/29 15:06
 */
@Data
public class TransConfiguration {

    private Boolean enableGlobal;

    private Boolean enableProxy;

    private static final TransConfiguration INSTANCE = new TransConfiguration();

    private TransConfiguration() {
    }

    public static TransConfiguration getInstance() {
        return INSTANCE;
    }
}
