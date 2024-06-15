//package com.project.springbatch.step5;
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
//import org.springframework.batch.repeat.RepeatStatus;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.transaction.PlatformTransactionManager;
//
//@Configuration
//public class Flow2 {
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
//                .start(flowA(jobRepository, transactionManager))
//                .next(step3(jobRepository, transactionManager))
//                .next(flowB(jobRepository, transactionManager))
//                .next(step6(jobRepository, transactionManager))
//                .end()
//                .build();
//    }
//
//
//    @Bean
//    public Flow flowA(JobRepository jobRepository, PlatformTransactionManager transactionManager){
//        FlowBuilder<Flow> flowFlowBuilder = new FlowBuilder<>("flowA");
//        return flowFlowBuilder.start(step1(jobRepository, transactionManager))
//                .next(step2(jobRepository, transactionManager))
//                .build();
//    }
//
//    @Bean
//    public Flow flowB(JobRepository jobRepository, PlatformTransactionManager transactionManager){
//        FlowBuilder<Flow> flowFlowBuilder = new FlowBuilder<>("flowB");
//        return flowFlowBuilder.start(step4(jobRepository, transactionManager))
//                .next(step5(jobRepository, transactionManager))
//                .build();
//    }
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
//    @Bean
//    public Step step4(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
//        return new StepBuilder("step4", jobRepository)
//                .tasklet(new Tasklet() {
//                    @Override
//                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
//                        System.out.println("start step4 hello world");
//                        return RepeatStatus.FINISHED;
//                    }
//                },transactionManager)
//                .build();
//    }
//
//    @Bean
//    public Step step5(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
//        return new StepBuilder("step5", jobRepository)
//                .tasklet(new Tasklet() {
//                    @Override
//                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
//                        System.out.println("start step5 hello world");
//                        return RepeatStatus.FINISHED;
//                    }
//                },transactionManager)
//                .build();
//    }
//
//    @Bean
//    public Step step6(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
//        return new StepBuilder("step6", jobRepository)
//                .tasklet(new Tasklet() {
//                    @Override
//                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
//                        System.out.println("start step6 hello world");
//                        return RepeatStatus.FINISHED;
//                    }
//                },transactionManager)
//                .build();
//    }
//}
