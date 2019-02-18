package ru.cft.task.crossingStreamline;

import lombok.Getter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import ru.cft.task.configuration.StreamlineConfiguration;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Objects;

@Component("LinkedListCrossingStreamline")
@ConditionalOnProperty(prefix = "streamline", name = "type", havingValue = "LinkedList")
public class LinkedListCrossingStreamline<T> implements CrossingStreamline<T> {

    private Node<T> firstA;
    private Node<T> firstB;

    @Getter
    private final Integer capacityA;

    @Getter
    private final Integer capacityB;

    private final Integer[] jointPointsA;
    private final Integer[] jointPointsB;

    public LinkedListCrossingStreamline(StreamlineConfiguration configuration) {
        capacityA = configuration.getCapacityA();
        capacityB = configuration.getCapacityB();
        jointPointsA = configuration.getJointPointsA();
        jointPointsB = configuration.getJointPointsB();
    }

    @PostConstruct
    private void init() {
        initStreams();
    }

    @Override
    public T pushA(T item) {
        firstA = push(firstA, item);
        interfereB();
        return removeLastFromA();
    }

    @Override
    public T pushB(T item) {
        firstB = push(firstB, item);
        interfereA();
        return removeLastFromB();
    }

    @Override
    public T[] getStreamA() {
        return toArray(capacityA, firstA);
    }

    @Override
    public T[] getStreamB() {
        return toArray(capacityB, firstB);
    }

    @Override
    public T getFromA(int index) {
        return getNodeFromA(index).item;
    }

    @Override
    public T getFromB(int index) {
        return getNodeFromB(index).item;
    }

    @Override
    public Integer getSizeA() {
        return (int) Arrays.stream(getStreamA())
                .filter(Objects::nonNull)
                .count();
    }

    @Override
    public Integer getSizeB() {
        return (int) Arrays.stream(getStreamB())
                .filter(Objects::nonNull)
                .count();
    }

    private T removeLastFromA() {
        Node<T> prev = getNodeFromA(capacityA - 1);
        return removeLast(prev);
    }

    private T removeLastFromB() {
        Node<T> prev = getNodeFromB(capacityB - 1);
        return removeLast(prev);
    }

    private T removeLast(Node<T> prev) {
        if (prev == null || prev.next == null) {
            return null;
        }
        T lastItem = prev.next.item;
        prev.next = null;
        return lastItem;
    }

    private Node<T> getNodeFromA(int index) {
        return getNode(firstA, index);
    }

    private Node<T> getNodeFromB(int index) {
        return getNode(firstB, index);
    }

    private Node<T> getNode(Node<T> stream, int index) {
        for (int i = 0; i < index; i++) {
            if (stream.next == null) {
                return new Node<>(null);
            }
            stream = stream.next;
        }
        return stream;
    }

    private Node<T> push(Node<T> node, T item) {
        Node<T> oldFirstNode = node;
        node = new Node<>(item);
        node.next = oldFirstNode;

        return node;
    }

    private void initStreams() {
        for (int i = 0; i < capacityA; i++) {
            firstA = push(firstA, null);
        }
        for (int i = 0; i < capacityB; i++) {
            firstB = push(firstB, null);
        }
    }

    @SuppressWarnings("unchecked")
    private T[] toArray(int size, Node<T> firstNode) {
        T[] result = (T[]) new Object[size];
        Node<T> x = firstNode;
        for (int i = 0; i < result.length; i++) {
            if (x != null) {
                result[i] = x.item;
                x = x.next;
            } else {
                result[i] = null;
            }
        }
        return result;
    }

    private void interfereB() {
        for (int i = 0; i < jointPointsA.length; i++) {
            getNode(firstB, jointPointsB[i]).item = getNode(firstA, jointPointsA[i]).item;
        }
    }

    private void interfereA() {
        for (int i = 0; i < jointPointsB.length; i++) {
            getNode(firstA, jointPointsA[i]).item = getNode(firstB, jointPointsB[i]).item;
        }
    }

    private class Node<T> {
        private Node<T> next;
        private T item;

        Node(T item) {
            this.item = item;
        }
    }

}
