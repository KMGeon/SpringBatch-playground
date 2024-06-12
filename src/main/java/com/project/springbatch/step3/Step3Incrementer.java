//package com.project.springbatch.step3;//package com.project.springbatch.step3;//package com.project.springbatch.step3;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.batch.core.*;
//import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
//import org.springframework.batch.core.job.builder.JobBuilder;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.core.step.builder.StepBuilder;
//import org.springframework.batch.repeat.RepeatStatus;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//@Configuration
//@RequiredArgsConstructor
//@EnableBatchProcessing
//public class Step3Incrementer {
//
//    @Bean
//    Job job(JobRepository jobRepository, Step step1, Step step2) {
//        return new JobBuilder("job12", jobRepository)
//                .start(step1)
//                .next(step2)
//                .incrementer(new CustomJobParametersIncrementer())
//                .build();
//    }
//
//    @Bean
//    Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
//        return new StepBuilder("step1", jobRepository)
//                .tasklet((contribution, chunkContext) -> {
//                    System.out.println("step1 was executed!");
//                    return RepeatStatus.FINISHED;
//                }, transactionManager)
//                .build();
//    }
//
//    @Bean
//    Step step2(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
//        return new StepBuilder("step2", jobRepository)
//                .tasklet((contribution, chunkContext) -> {
//                    JobParameters jobParameters = contribution.getStepExecution().getJobExecution().getJobParameters();
//                    System.out.println("step2 was executed!");
//                    return RepeatStatus.FINISHED;
//                }, transactionManager)
//                .build();
//    }
//}
//
//class CustomJobParametersIncrementer implements JobParametersIncrementer{
//
//    static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyyMMdd-hhmmss");
//
//    @Override
//    public JobParameters getNext(JobParameters parameters) {
//        String id =  SIMPLE_DATE_FORMAT.format(new Date());
//        return new JobParametersBuilder().addString("run.id", id).toJobParameters();
//    }
//}