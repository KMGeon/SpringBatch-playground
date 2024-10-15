package com.project.springbatch.step12;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

import java.time.Duration;
import java.time.LocalDateTime;

public class CustomJobExecutionListener implements JobExecutionListener {
    @Override
    public void beforeJob(JobExecution jobExecution) {
        System.out.println("First Job Start");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        LocalDateTime startTime = jobExecution.getStartTime();
        LocalDateTime endTime = jobExecution.getEndTime();

        if (startTime != null && endTime != null) {
            Duration duration = Duration.between(startTime, endTime);

            long seconds = duration.getSeconds();
            long millis = duration.toMillis() % 1000;

            System.out.printf("Job 실행 시간: %d초 %d밀리초%n", seconds, millis);
        } else {
            System.out.println("시작 시간 또는 종료 시간이 null입니다.");
        }
    }
}
