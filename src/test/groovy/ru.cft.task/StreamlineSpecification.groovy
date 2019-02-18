package ru.cft.task

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import ru.cft.task.configuration.StreamlineConfiguration
import ru.cft.task.configuration.TestConfig
import spock.lang.Specification

@SpringBootTest(classes = [Application, TestConfig])
@ActiveProfiles("test")
class StreamlineSpecification extends Specification {

    @Autowired
    StreamlineConfiguration streamlineConfiguration

}
