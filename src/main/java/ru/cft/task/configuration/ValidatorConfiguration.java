package ru.cft.task.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.valid4j.Validator;

import javax.validation.ConstraintViolationException;

@Configuration
public class ValidatorConfiguration {

    @Bean
    public Validator<ConstraintViolationException> valid4jValidator() {
        return new Validator<>(msg -> new ConstraintViolationException(msg, null));
    }

}
