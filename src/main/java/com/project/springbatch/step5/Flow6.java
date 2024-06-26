//package com.project.springbatch.step5;
//
//import com.project.springbatch.DEFAILT_ENUM;
//import org.springframework.batch.core.*;
//import org.springframework.batch.core.job.builder.JobBuilder;
//import org.springframework.batch.core.job.flow.FlowExecutionStatus;
//import org.springframework.batch.core.job.flow.JobExecutionDecider;
//import org.springframework.batch.core.launch.support.RunIdIncrementer;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.core.scope.context.ChunkContext;
//import org.springframework.batch.core.step.builder.StepBuilder;
//import org.springframework.batch.core.step.tasklet.Tasklet;
//import org.springframework.batch.repeat.RepeatStatus;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.transaction.PlatformTransactionManager;
//
///**
// * JobExecutionDecider
// * - On은 exitStatus의 값을 비교하는 것이 아닌 string pattern에 대해서 매칭한다.
// * FlowExecutionStatus는 내부적으로 string을 반환한다.
// */
//@Configuration
//public class Flow6 {
//
//
//    @Bean
//    public Job job(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
//        return new JobBuilder("batchJob", jobRepository)
//                .incrementer(new RunIdIncrementer())
//                .start(step1(jobRepository, transactionManager))
//                .next(decider())
//                .from(decider()).on("ODD").to(oddStep(jobRepository, transactionManager))
//                .from(decider()).on("EVEN").to(eventStep(jobRepository, transactionManager))
//                .end()
//                .build();
//    }
//
//    @Bean
//    public JobExecutionDecider decider() {
//        return new CustomDecider();
//    }
//
//
//    @Bean
//    public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
//        return new StepBuilder("step1", jobRepository)
//                .tasklet(new Tasklet() {
//                    @Override
//                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
//                        System.out.println("start step1 hello world");
//                        return RepeatStatus.FINISHED;
//                    }
//                }, transactionManager)
//                .build();
//    }
//
//
//    @Bean
//    public Step eventStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
//        return new StepBuilder("eventStep", jobRepository)
//                .tasklet(new Tasklet() {
//                    @Override
//                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
//                        System.out.println("hi even Step");
//
//                        return RepeatStatus.FINISHED;
//                    }
//                }, transactionManager)
//                .build();
//    }
//
//    @Bean
//    public Step oddStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
//        return new StepBuilder("oddStep", jobRepository)
//                .tasklet(new Tasklet() {
//                    @Override
//                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
//                        System.out.println("hi odd Step");
//
//                        return RepeatStatus.FINISHED;
//                    }
//                }, transactionManager)
//                .build();
//    }
//}
//
//class CustomDecider implements JobExecutionDecider {
//
//    private int count = 1;
//
//    @Override
//    public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
//
//        count++;
//
//        if (count % 2 == 0)
//            return new FlowExecutionStatus("EVEN");
//        else return new FlowExecutionStatus("ODD");
//
//    }
//}
