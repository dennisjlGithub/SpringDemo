package com.test.util;

import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTask {

    @Scheduled(fixedRate = 60*1000)
    public void scheduledTask() {
        System.out.println("ScheduledTask: " + LocalDateTime.now());
    }
}
