package com.fly.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author 谢飞
 * @since 2023/6/9 11:57
 */
@SpringBootApplication
public class ExampleApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ExampleApplication.class, args);
        System.out.printf("http://localhost:%s/doc.html\n", context.getEnvironment().getProperty("server.port"));
    }
}
