package ru.cft.task.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.cft.task.crossingStreamline.CrossingStreamline;
import ru.cft.task.entities.GeneralStatistics;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class StreamlineService<T> {

    private final CrossingStreamline<T> streamline;

    @SuppressWarnings("unchecked")
    public GeneralStatistics<T> getStats() {
        return (GeneralStatistics<T>) GeneralStatistics.builder()
                .streamA(Arrays.asList(streamline.getStreamA()))
                .streamB(Arrays.asList(streamline.getStreamB()))
                .capacityA(streamline.getCapacityA())
                .capacityB(streamline.getCapacityB())
                .sizeA(streamline.getSizeA())
                .sizeB(streamline.getSizeB())
                .build();
    }

    public T pushA(T item) {
        return streamline.pushA(item);
    }

    public T pushB(T item) {
        return streamline.pushB(item);
    }

}
