package com.project.springbatch.step5;

import com.project.springbatch.DEFAILT_ENUM;
import org.springframework.batch.core.*;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * 사용자 정의 ExitStatus
 * step1이 실패되면 step2를 실행한다.
 * 이때 step2의 exitstatus코드가 custom이면 stop을 한다.
 *
 * - 여기서 listener를 통해서 failed일때 status를 바꿀 수 있다.
 */
@Configuration
public class Flow6 {


    @Bean
    public Job flowJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobBuilder("batchJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(step1(jobRepository, transactionManager))
                .on(DEFAILT_ENUM.FAILED.getValue())
                .to(step2(jobRepository, transactionManager))
                .on(DEFAILT_ENUM.CUSTOM_EXIT_STATUS_CODE.getValue())
                .stop()
                .end()
                .build();
    }


    @Bean
    public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("step1", jobRepository)
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        contribution.setExitStatus(ExitStatus.FAILED);
                        System.out.println("start step1 hello world");
                        return RepeatStatus.FINISHED;
                    }
                }, transactionManager)
                .build();
    }


    @Bean
    public Step step2(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("step2", jobRepository)
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("start step2 hello world");
                        contribution.setExitStatus(ExitStatus.FAILED);
                        return RepeatStatus.FINISHED;
                    }
                }, transactionManager)
                .listener(new PassCheckListener())
                .build();
    }
}
