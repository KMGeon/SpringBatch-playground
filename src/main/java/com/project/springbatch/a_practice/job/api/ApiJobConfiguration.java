//package com.project.springbatch.a_practice.job.api;
//
//import com.project.springbatch.a_practice.domain.Product;
//import com.project.springbatch.a_practice.domain.ProductVO;
//import com.project.springbatch.a_practice.listener.JobListener;
//import com.project.springbatch.a_practice.tasklet.ApiEndTasklet;
//import com.project.springbatch.a_practice.tasklet.ApiStartTasklet;
//import lombok.RequiredArgsConstructor;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.job.builder.JobBuilder;
//import org.springframework.batch.core.launch.support.RunIdIncrementer;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.core.step.builder.StepBuilder;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.transaction.PlatformTransactionManager;
//
//@Configuration
//@RequiredArgsConstructor
//public class ApiJobConfiguration {
//    private final JobRepository jobRepository;
//    private final PlatformTransactionManager transactionManager;
//
//    private final ApiStartTasklet apiStartTasklet;
//    private final ApiEndTasklet apiEndTasklet;
//
//    private final Step jobStep;
//
//    @Qualifier("sqlSessionTemplate")
//    private final SqlSessionTemplate sqlSessionTemplate;
//
//    private static final int CHUNK_SIZE = 10;
//
//    @Bean
//    public Job apiJob() {
//        return new JobBuilder("apiJob", jobRepository)
//                .incrementer(new RunIdIncrementer())
//                .listener(new JobListener())
//                .start(apiStep1())
//                .next(jobStep)
//                .next(apiStep2())
//                .build();
//    }
//
//
//    @Bean
//    public Step apiStep1() {
//        return new StepBuilder("apiStep1", jobRepository)
//                .tasklet(apiStartTasklet, transactionManager)
//                .build();
//    }
//
//    @Bean
//    public Step apiStep2() {
//        return new StepBuilder("apiStep2", jobRepository)
//                .tasklet(apiEndTasklet, transactionManager)
//                .build();
//    }
//}
