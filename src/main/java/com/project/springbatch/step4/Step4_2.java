//package com.project.springbatch.step4;//package com.project.springbatch.step3;
//
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.StepContribution;
//import org.springframework.batch.core.job.builder.FlowBuilder;
//import org.springframework.batch.core.job.builder.JobBuilder;
//import org.springframework.batch.core.job.flow.Flow;
//import org.springframework.batch.core.launch.support.RunIdIncrementer;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.core.scope.context.ChunkContext;
//import org.springframework.batch.core.step.builder.StepBuilder;
//import org.springframework.batch.core.step.tasklet.Tasklet;
//import org.springframework.batch.item.Chunk;
//import org.springframework.batch.item.ItemProcessor;
//import org.springframework.batch.item.ItemWriter;
//import org.springframework.batch.item.support.ListItemReader;
//import org.springframework.batch.repeat.RepeatStatus;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import java.util.Arrays;
//
//
///**
// * StepBuilder는 하위 api를 판별하여 각각마다 올바른 Builder를 생성한다.
// */
//@Configuration
//@Component
//public class Step4_2 {
//
//    @Bean
//    Job job(JobRepository jobRepository, Step step01, Step step02) {
//        return new JobBuilder("step4_2", jobRepository)
//                .start(step01)
//                .next(step02)
//                .build();
//    }
//
//    @Bean
//    Step step01(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
//        return new StepBuilder("step1", jobRepository)// StepBuilder를 생성하여 Step의 이름을 매개변수로 받는다.
//                .tasklet(new Tasklet() {
//                    @Override
//                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
//                        System.out.println("Step 01 start =================");
//                        return RepeatStatus.FINISHED;
//                    }
//                }, transactionManager)
//                .allowStartIfComplete(true)
//                .build();
//    }
//
//    @Bean
//    Step step02(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
//        return new StepBuilder("step2", jobRepository)
//                .tasklet(new Tasklet() {
//                    @Override
//                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
//
//                        System.out.println("Step02 Start ===================");
//                        System.out.println("contribution = " + contribution);
//                        System.out.println("chunkContext = " + chunkContext);
//                        throw new RuntimeException("step2 exception");
//                    }
//                }, transactionManager)
//                .startLimit(3)
//                .build();
//    }
//
//
//}
