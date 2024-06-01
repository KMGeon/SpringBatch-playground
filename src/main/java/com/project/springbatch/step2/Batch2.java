package com.project.springbatch.step2;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class Batch2 {

//    @Bean
//    ApplicationRunner runner(JobLauncher jobLauncher, Job job) {
//        return new ApplicationRunner() {
//            @Override
//            public void run(ApplicationArguments args) throws Exception {
//                jobLauncher.run(job, new JobParametersBuilder()
//                        .addString("UUID", UUID.randomUUID().toString())
//                        .toJobParameters());
//            }
//        };
//    }

    @Bean
    Job job(JobRepository jobRepository, Step step1, Step step2) {
        return new JobBuilder("job", jobRepository)
                .start(step1)
                .next(step2)
                .build();
    }

    @Bean
    Step step2(JobRepository jobRepository, Tasklet tasklet2, PlatformTransactionManager transactionManager) {
        return new StepBuilder("step2", jobRepository)
                .tasklet(tasklet2, transactionManager)
                .build();
    }


    @Bean
    Step step1(JobRepository jobRepository, Tasklet tasklet1, PlatformTransactionManager transactionManager) {
        return new StepBuilder("step1", jobRepository)
                .tasklet(tasklet1, transactionManager)
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

    /**

     */
}
