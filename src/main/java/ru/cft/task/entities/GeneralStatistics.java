package ru.cft.task.entities;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class GeneralStatistics<T> {

    Integer capacityA;
    Integer capacityB;
    Integer sizeA;
    Integer sizeB;
    List<T> streamA;
    List<T> streamB;

}
