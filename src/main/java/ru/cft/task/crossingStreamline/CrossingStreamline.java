package ru.cft.task.crossingStreamline;

public interface CrossingStreamline<T> {

    T pushA(T item);

    T pushB(T item);

    T[] getStreamA();

    T[] getStreamB();

    T getFromA(int index);

    T getFromB(int index);

    Integer getCapacityA();

    Integer getCapacityB();

    Integer getSizeA();

    Integer getSizeB();

}
