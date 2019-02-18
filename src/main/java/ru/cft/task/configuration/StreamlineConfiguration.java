package ru.cft.task.configuration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import ru.cft.task.validators.StreamlineValidator;

import javax.annotation.PostConstruct;

@ConfigurationProperties(prefix = "streamline")
@RequiredArgsConstructor
@Getter
@Setter
public class StreamlineConfiguration {

    private final StreamlineValidator validator;

    private Integer capacityA;
    private Integer capacityB;
    private Integer[] jointPointsA;
    private Integer[] jointPointsB;

    @PostConstruct
    public void validate() {
        validator.validateStreamlineConfiguration(
                jointPointsA, jointPointsB, capacityA, capacityB);
    }
}
