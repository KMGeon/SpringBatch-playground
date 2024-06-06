//package com.project.springbatch.step2;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.StepContribution;
//import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
//import org.springframework.batch.core.job.builder.JobBuilder;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.core.scope.context.ChunkContext;
//import org.springframework.batch.core.step.builder.StepBuilder;
//import org.springframework.batch.core.step.tasklet.Tasklet;
//import org.springframework.batch.item.ExecutionContext;
//import org.springframework.batch.repeat.RepeatStatus;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.PlatformTransactionManager;
//
//@EnableBatchProcessing
//@Configuration
//@RequiredArgsConstructor
//public class Batch4_ExecutionContext {
//
//    private final ExecutionContextTasklet1 executionContextTasklet1;
//    private final ExecutionContextTasklet2 executionContextTasklet2;
//    private final ExecutionContextTasklet3 executionContextTasklet3;
//    private final ExecutionContextTasklet4 executionContextTasklet4;
//
//    @Bean
//    Job job(JobRepository jobRepository, Step step1, Step step2, Step step3, Step step4) {
//        return new JobBuilder("exJob3", jobRepository)
//                .start(step1)
//                .next(step2)
//                .next(step3)
//                .next(step4)
//                .build();
//    }
//
//
//    @Bean
//    Step step1(JobRepository jobRepository,  PlatformTransactionManager transactionManager) {
//        return new StepBuilder("step1", jobRepository)
//                .tasklet(executionContextTasklet1, transactionManager)
//                .build();
//    }
//
//
//    @Bean
//    Step step2(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
//        return new StepBuilder("step2", jobRepository)
//                .tasklet(executionContextTasklet2, transactionManager)
//                .build();
//    }
//
//    @Bean
//    Step step3(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
//        return new StepBuilder("step3", jobRepository)
//                .tasklet(executionContextTasklet3, transactionManager)
//                .build();
//    }
//
//
//    @Bean
//    Step step4(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
//        return new StepBuilder("step4", jobRepository)
//                .tasklet(executionContextTasklet4, transactionManager)
//                .build();
//    }
//
//
//}
//
//@Component
//class ExecutionContextTasklet1 implements Tasklet {
//    @Override
//    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
//        System.out.println("ExecutionContextTasklet1");
//
//        ExecutionContext jobExecutionContext = stepContribution.getStepExecution().getJobExecution().getExecutionContext();
//        ExecutionContext stepExecutionContext = stepContribution.getStepExecution().getExecutionContext();
//
//        String jobName = chunkContext.getStepContext().getStepExecution().getJobExecution().getJobInstance().getJobName();
//        String stepName = chunkContext.getStepContext().getStepExecution().getStepName();
//
//        if (jobExecutionContext.get("jobName") == null) jobExecutionContext.put("jobName", jobName);
//        if (stepExecutionContext.get("stepName")== null)stepExecutionContext.put("stepName", stepName);
//
//        System.out.println("jobName >> :" + jobExecutionContext.get("jobName"));
//        System.out.println("stepName >> :" + jobExecutionContext.get("stepName"));
//
//
//        return RepeatStatus.FINISHED;
//    }
//}
//
//@Component
//class ExecutionContextTasklet2 implements Tasklet {
//    @Override
//    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
//        System.out.println("ExecutionContextTasklet2");
//
//        ExecutionContext jobExecutionContext = stepContribution.getStepExecution().getJobExecution().getExecutionContext();
//        ExecutionContext stepExecutionContext = stepContribution.getStepExecution().getExecutionContext();
//
//        System.out.println("jobName >> :" + jobExecutionContext.get("jobName"));
//        System.out.println("stepName >> :" + jobExecutionContext.get("stepName"));
//
//        return RepeatStatus.FINISHED;
//    }
//}
//
//@Component
//class ExecutionContextTasklet3 implements Tasklet {
//    @Override
//    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
//        System.out.println("ExecutionContextTasklet3");
//
//        ExecutionContext jobExecutionContext = stepContribution.getStepExecution().getJobExecution().getExecutionContext();
//        ExecutionContext stepExecutionContext = stepContribution.getStepExecution().getExecutionContext();
//
//        System.out.println("jobName >> :" + jobExecutionContext.get("jobName"));
//        System.out.println("stepName >> :" + jobExecutionContext.get("stepName"));
//
//
//        return RepeatStatus.FINISHED;
//    }
//}
//
//@Component
//class ExecutionContextTasklet4 implements Tasklet {
//    @Override
//    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
//        System.out.println("ExecutionContextTasklet4");
//
//        ExecutionContext jobExecutionContext = stepContribution.getStepExecution().getJobExecution().getExecutionContext();
//        ExecutionContext stepExecutionContext = stepContribution.getStepExecution().getExecutionContext();
//
//        System.out.println("jobName >> :" + jobExecutionContext.get("jobName"));
//        System.out.println("stepName >> :" + jobExecutionContext.get("stepName"));
//
//        return RepeatStatus.FINISHED;
//    }
//}