package ru.cft.task.validators;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.valid4j.Validator;

import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.IntSummaryStatistics;

@Component
@RequiredArgsConstructor
public class StreamlineValidator {

    private final Validator<ConstraintViolationException> valid4jValidator;

    @SneakyThrows
    public void validateStreamlineConfiguration(
            Integer[] jointPointsA, Integer[] jointPointsB, Integer capacityA, Integer capacityB) {

        valid4jValidator.validate(jointPointsA.length == jointPointsB.length,
                "Number of point pairs must be mutually equal");
        valid4jValidator.validate(ifNoRepetitions(jointPointsA, jointPointsB),
                "None joint points array must contain duplicates");
        valid4jValidator.validate(ifSorted(jointPointsA, jointPointsB),
                "Joint points within arrays must be sorted in increasing order");
        valid4jValidator.validate(ifPointsAreWithinBounds(jointPointsA, jointPointsB, capacityA, capacityB),
                "Joint point must be within capacity bounds");
    }

    private boolean ifNoRepetitions(Integer[] jointPointsA, Integer[] jointPointsB) {
        return Arrays.stream(jointPointsA).distinct().count() == jointPointsA.length &&
                Arrays.stream(jointPointsB).distinct().count() == jointPointsB.length;
    }

    private boolean ifSorted(Integer[] jointPointsA, Integer[] jointPointsB) {
        return Arrays.equals(Arrays.stream(jointPointsA).sorted().toArray(), (jointPointsA)) &&
                Arrays.equals(Arrays.stream(jointPointsB).sorted().toArray(), (jointPointsB));
    }

    private boolean ifPointsAreWithinBounds(
            Integer[] jointPointsA, Integer[] jointPointsB, Integer capacityA, Integer capacityB) {
        IntSummaryStatistics aStats = Arrays.stream(jointPointsA)
                .mapToInt(Integer::intValue)
                .summaryStatistics();
        IntSummaryStatistics bStats = Arrays.stream(jointPointsB)
                .mapToInt(Integer::intValue)
                .summaryStatistics();

        return aStats.getMax() < capacityA && aStats.getMin() > -1 &&
                bStats.getMax() < capacityB && bStats.getMin() > -1;

    }

}
