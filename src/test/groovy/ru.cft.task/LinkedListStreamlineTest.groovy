package ru.cft.task

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.TestPropertySource
import ru.cft.task.crossingStreamline.LinkedListCrossingStreamline

@TestPropertySource(properties = "streamline.type=LinkedList")
class LinkedListStreamlineTest extends StreamlineSpecification {

    @Autowired
    LinkedListCrossingStreamline<Integer> linkedListCrossingStreamline

    def "Should be empty on startup"() {
        when:
        def streamA = linkedListCrossingStreamline.streamA
        def streamB = linkedListCrossingStreamline.streamB

        then:
        streamA == [null, null, null, null, null, null]
        streamB == [null, null, null]
    }

    def "Should remove last item from stream A when pushed"() {

        when:
        def a
        7.times { a = linkedListCrossingStreamline.pushA(it) }

        then:
        a == 0
    }

    def "Should remove last item from stream B when pushed"() {

        when:
        def b
        4.times { b = linkedListCrossingStreamline.pushB(it) }

        then:
        b == 0
    }

    def "A should interfere joint points of B"() {
        given:
        emptyStreamline()

        when:
        6.times { linkedListCrossingStreamline.pushA(it) }

        then:
        linkedListCrossingStreamline.getFromB(0) == 3
        linkedListCrossingStreamline.getFromB(1) == 2
        linkedListCrossingStreamline.getFromB(2) == 0
    }

    def "B should interfere joint points of A"() {
        given:
        emptyStreamline()

        when:
        3.times { linkedListCrossingStreamline.pushB(it) }

        then:
        linkedListCrossingStreamline.getFromA(2) == 2
        linkedListCrossingStreamline.getFromA(3) == 1
        linkedListCrossingStreamline.getFromA(5) == 0
    }

    def "Should change size after adding non-null values"() {
        given:
        emptyStreamline()

        when:
        3.times { linkedListCrossingStreamline.pushA(it) }

        then:
        linkedListCrossingStreamline.sizeA == 3
    }

    void emptyStreamline() {
        linkedListCrossingStreamline.capacityA.times { linkedListCrossingStreamline.pushA(null) }
        linkedListCrossingStreamline.capacityB.times { linkedListCrossingStreamline.pushA(null) }
    }
}
