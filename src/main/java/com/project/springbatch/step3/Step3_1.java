package com.project.springbatch.step3;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.UUID;

@Configuration
@Component
public class Step3_1 {


    @Bean
    ApplicationRunner runner(JobLauncher jobLauncher, Job job, Job job2) {
        return new ApplicationRunner() {
            @Override
            public void run(ApplicationArguments args) throws Exception {
                Step3_1 step31 = new Step3_1();
                jobLauncher.run(job, new JobParametersBuilder()
                        .addString("UUID", UUID.randomUUID().toString())
                        .toJobParameters());

                jobLauncher.run(job2, new JobParametersBuilder()
                        .addString("UUID", UUID.randomUUID().toString())
                        .toJobParameters());
            }
        };
    }

    @Bean
    Job job(JobRepository jobRepository, Step step1, Step step2) {
        return new JobBuilder("job1", jobRepository)
                .start(step1)
                .next(step2)
                .build();
    }

    @Bean
    Job job2(JobRepository jobRepository, Step step1, Step step2) {
        return new JobBuilder("job2", jobRepository)
                .start(step1)
                .next(step2)
                .build();
    }

    @Bean
    Step step1(JobRepository jobRepository, Tasklet tasklet1, PlatformTransactionManager transactionManager) {
        return new StepBuilder("step1", jobRepository)
                .tasklet(tasklet1, transactionManager)
                .build();
    }


    @Bean
    Step step2(JobRepository jobRepository, Tasklet tasklet2, PlatformTransactionManager transactionManager) {
        return new StepBuilder("step2", jobRepository)
                .tasklet(tasklet2, transactionManager)
                .build();
    }

    @Bean
    Tasklet tasklet1() {
        return (contribution, chunkContext) -> {
            System.out.println("step1 was excuted!");
            return RepeatStatus.FINISHED;
        };
    }

    @Bean
    Tasklet tasklet2() {
        return (contribution, chunkContext) -> {
            System.out.println("step2 was excuted!");
            return RepeatStatus.FINISHED;
        };
    }

}
