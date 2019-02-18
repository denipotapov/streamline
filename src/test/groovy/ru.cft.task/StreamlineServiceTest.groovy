package ru.cft.task

import org.springframework.beans.factory.annotation.Autowired
import ru.cft.task.entities.GeneralStatistics
import ru.cft.task.services.StreamlineService

class StreamlineServiceTest extends StreamlineSpecification {

    @Autowired
    StreamlineService streamlineService

    def "Should get general statistics about streams"() {

        when:
        def expectedStats =
                new GeneralStatistics(6, 3, 5, 3, [1, 8, 2, 3, null, 3], [2, 3, 3])

        streamlineService.pushA(3)
        streamlineService.pushA(8)
        streamlineService.pushA(1)
        streamlineService.pushB(3)
        streamlineService.pushB(2)

        then:

        streamlineService.stats == expectedStats

    }
}
