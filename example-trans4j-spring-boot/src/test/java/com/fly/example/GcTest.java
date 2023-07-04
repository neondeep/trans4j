package com.fly.example;

import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;

/**
 * @author 谢飞
 * @since 2023/6/28 17:57
 */
@Slf4j
public class GcTest {

    @Test
    @DisplayName("无操作")
    public void a000Test() {
        for (int i = 0; i < 1000; i++) {
            HttpUtil.get("http://localhost:7001/test/a000");
        }
    }

    @Test
    @DisplayName("ref")
    public void a001Test() {
        Instant now = Instant.now();
        for (int i = 0; i < 10000; i++) {
            HttpUtil.get("http://localhost:7001/test/a001");
        }
        log.info("使用秒：{}", Duration.between(now, Instant.now()).getSeconds());
    }

    @Test
    @DisplayName("proxy")
    public void a002Test() {
        Instant now = Instant.now();
        for (int i = 0; i < 10000; i++) {
            HttpUtil.get("http://localhost:7001/test/a002");
        }
        log.info("使用秒：{}", Duration.between(now, Instant.now()).getSeconds());
    }
}
