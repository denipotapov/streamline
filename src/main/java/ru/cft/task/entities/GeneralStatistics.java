package ru.cft.task.entities;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class GeneralStatistics<T> {

    private Integer capacityA;
    private Integer capacityB;
    private Integer sizeA;
    private Integer sizeB;
    private List<T> streamA;
    private List<T> streamB;

}
