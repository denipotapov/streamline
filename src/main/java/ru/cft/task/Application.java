package ru.cft.task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.cft.task.configuration.StreamlineConfiguration;

@SpringBootApplication
@EnableConfigurationProperties(StreamlineConfiguration.class)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}

