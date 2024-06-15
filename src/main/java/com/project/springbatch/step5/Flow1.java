//package com.project.springbatch.step5;
//
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.StepContribution;
//import org.springframework.batch.core.job.builder.JobBuilder;
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
//@Configuration
//public class Flow1 {
//
//
//    /**
//     *   simplejobbuilder > on > flwojobbuilder를 생성하고 > trasnsitionbuilder 생성 ( 상태에 따라서 전환 )
//     *   > end > simpleflowbuiler 생성 > 최종적으로 flowjob생성 (simpleflow)저장 >
//     */
//    @Bean
//    public Job parentJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
//        return new JobBuilder("batchJob", jobRepository)
//                .incrementer(new RunIdIncrementer())
//                .start(step1(jobRepository, transactionManager))
//                .on(DEFAILT_ENUM.COMPLETED.getValue()).to(step3(jobRepository, transactionManager))
//                .from(step1(jobRepository, transactionManager))
//                .on(DEFAILT_ENUM.FAILED.getValue()).to(step2(jobRepository, transactionManager))
//                .end()
//                .build();
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
//                },transactionManager)
//                .build();
//    }
//
//
//    @Bean
//    public Step step2(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
//        return new StepBuilder("step2", jobRepository)
//                .tasklet(new Tasklet() {
//                    @Override
//                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
//                        System.out.println("start step2 hello world");
//                        return RepeatStatus.FINISHED;
//                    }
//                },transactionManager)
//                .build();
//    }
//
//    @Bean
//    public Step step3(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
//        return new StepBuilder("step3", jobRepository)
//                .tasklet(new Tasklet() {
//                    @Override
//                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
//                        System.out.println("start step3 hello world");
//                        return RepeatStatus.FINISHED;
//                    }
//                },transactionManager)
//                .build();
//    }
//
//
//}
