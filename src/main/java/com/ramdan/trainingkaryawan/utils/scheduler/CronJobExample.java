package com.ramdan.trainingkaryawan.utils.scheduler;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Component
public class CronJobExample {

    @Autowired //backround [proccses
    @Qualifier(value = "taskExecutor")
    private TaskExecutor taskExecutor;

    @Scheduled(cron = "${cron.expression:-}")
    public void sendAsync() {
        taskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("Method executed at every 2 weeks. Current time is :: " + new Date());

            }
        });
    }
}
