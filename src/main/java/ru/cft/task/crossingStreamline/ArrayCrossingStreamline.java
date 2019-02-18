package ru.cft.task.crossingStreamline;

import lombok.Getter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import ru.cft.task.configuration.StreamlineConfiguration;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Objects;

@Component("ArrayCrossingStreamline")
@ConditionalOnProperty(prefix = "streamline", name = "type", havingValue = "Array", matchIfMissing = true)
public class ArrayCrossingStreamline<T> implements CrossingStreamline<T> {

    @Getter
    private T[] streamA;

    @Getter
    private T[] streamB;

    @Getter
    private final Integer capacityA;

    @Getter
    private final Integer capacityB;

    private final Integer[] jointPointsA;
    private final Integer[] jointPointsB;

    public ArrayCrossingStreamline(StreamlineConfiguration configuration) {
        capacityA = configuration.getCapacityA();
        capacityB = configuration.getCapacityB();
        jointPointsA = configuration.getJointPointsA();
        jointPointsB = configuration.getJointPointsB();
    }

    @PostConstruct
    private void init() {
        initStreams();
    }

    @SuppressWarnings("unchecked")
    private void initStreams() {
        streamA = (T[]) new Object[capacityA];
        streamB = (T[]) new Object[capacityB];
    }

    @Override
    public T pushA(T item) {
        T lastItem = streamA[capacityA - 1];
        streamA = push(streamA, item);
        interfereB();

        return lastItem;
    }

    @Override
    public T pushB(T item) {
        T lastItem = streamB[capacityB - 1];
        streamB = push(streamB, item);
        interfereA();

        return lastItem;
    }

    @Override
    public T getFromA(int index) {
        return streamA[index];
    }

    @Override
    public T getFromB(int index) {
        return streamB[index];
    }

    @Override
    public Integer getSizeA() {
        return (int) Arrays.stream(streamA)
                .filter(Objects::nonNull)
                .count();
    }

    @Override
    public Integer getSizeB() {
        return (int) Arrays.stream(streamB)
                .filter(Objects::nonNull)
                .count();
    }

    private void interfereB() {
        for (int i = 0; i < jointPointsA.length; i++) {
            streamB[jointPointsB[i]] = streamA[jointPointsA[i]];
        }
    }

    private void interfereA() {
        for (int i = 0; i < jointPointsB.length; i++) {
            streamA[jointPointsA[i]] = streamB[jointPointsB[i]];
        }
    }

    private T[] push(T[] src, T item) {
        Object[] result = new Object[src.length];
        System.arraycopy(src, 0, result, 1, src.length - 1);
        result[0] = item;

        return (T[]) result;
    }
}
