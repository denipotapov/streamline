package ru.cft.task.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.cft.task.entities.GeneralStatistics;
import ru.cft.task.services.StreamlineService;

@RestController
@RequestMapping("/api/v1/integer-streamline")
@RequiredArgsConstructor
@Log
public class IntegerStreamlineController {

    private final StreamlineService<Integer> streamlineService;

    @GetMapping(value = "/get-statistics", produces = "application/json")
    public GeneralStatistics getStatistics() {
        GeneralStatistics statistics = streamlineService.getStats();
        log.info("/get-statistics Response: " + statistics.toString());
        return statistics;
    }

    @PostMapping(value = "/push-a")
    public Integer pushA(@RequestParam Integer item) {
        log.info("/push-a Request:" + item);
        Integer removed = streamlineService.pushA(item);
        log.info("/push-a Response:" + removed);
        return removed;
    }

    @PostMapping(value = "/push-b")
    public Integer pushB(@RequestParam Integer item) {
        log.info("/push-b Request: " + item);
        Integer removed = streamlineService.pushB(item);
        log.info("/push-b Response:" + removed);
        return removed;
    }
}
