package ru.cft.task

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.TestPropertySource
import ru.cft.task.crossingStreamline.ArrayCrossingStreamline

@TestPropertySource(properties = "streamline.type=Array")
class ArrayCrossingStreamlineTest extends StreamlineSpecification {

    @Autowired
    ArrayCrossingStreamline<Integer> arrayCrossingStreamline

    def "Should be empty on startup"() {
        when:
        def streamA = arrayCrossingStreamline.streamA
        def streamB = arrayCrossingStreamline.streamB

        then:
        streamA == [null, null, null, null, null, null]
        streamB == [null, null, null]
    }

    def "Should remove last item from stream A when pushed"() {

        when:
        def a
        7.times { a = arrayCrossingStreamline.pushA(it) }

        then:
        a == 0
    }

    def "Should remove last item from stream B when pushed"() {

        when:
        def b
        4.times { b = arrayCrossingStreamline.pushB(it) }

        then:
        b == 0
    }

    def "A should interfere joint points of B"() {
        given:
        emptyStreamline()

        when:
        6.times { arrayCrossingStreamline.pushA(it) }

        then:
        arrayCrossingStreamline.getFromB(0) == 3
        arrayCrossingStreamline.getFromB(1) == 2
        arrayCrossingStreamline.getFromB(2) == 0
    }

    def "B should interfere joint points of A"() {
        given:
        emptyStreamline()

        when:
        3.times { arrayCrossingStreamline.pushB(it) }

        then:
        arrayCrossingStreamline.getFromA(2) == 2
        arrayCrossingStreamline.getFromA(3) == 1
        arrayCrossingStreamline.getFromA(5) == 0
    }

    def "Should change size after adding non-null values"() {
        given:
        emptyStreamline()

        when:
        3.times { arrayCrossingStreamline.pushA(it) }

        then:
        arrayCrossingStreamline.sizeA == 3
    }

    void emptyStreamline() {
        arrayCrossingStreamline.capacityA.times { arrayCrossingStreamline.pushA(null) }
        arrayCrossingStreamline.capacityB.times { arrayCrossingStreamline.pushA(null) }
    }

}
