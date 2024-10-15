//package com.project.springbatch.a_practice.job.api;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.job.builder.JobBuilder;
//import org.springframework.batch.core.launch.JobLauncher;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.core.step.builder.StepBuilder;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.transaction.PlatformTransactionManager;
//
//@Configuration
//@RequiredArgsConstructor
//public class ApiJobChildConfiguration {
//    private final JobRepository jobRepository;
//    private final PlatformTransactionManager transactionManager;
//    private final JobLauncher jobLauncher;
//
//    @Qualifier("jobStep")
//    private final Step apiMasterStep;
//
//    @Bean
//    public Step jobStep(){
//        return new StepBuilder("jobStep", jobRepository)
//                .job(childJob())
//                .launcher(jobLauncher)
//                .build();
//    }
//
//    @Bean
//    public Job childJob(){
//        return new JobBuilder("childJob", jobRepository)
//                .start(apiMasterStep)
//                .build();
//    }
//}
