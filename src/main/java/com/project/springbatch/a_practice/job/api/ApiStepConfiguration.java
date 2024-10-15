//package com.project.springbatch.a_practice.job.api;
//
//import com.project.springbatch.a_practice.domain.ProductVO;
//import com.project.springbatch.a_practice.partition.ProductPartitioner;
//import com.project.springbatch.web.model.CustomerVO;
//import lombok.RequiredArgsConstructor;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.core.step.builder.StepBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.task.SimpleAsyncTaskExecutor;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import javax.sql.DataSource;
//
//@Configuration
//@RequiredArgsConstructor
//public class ApiStepConfiguration {
//    private final JobRepository jobRepository;
//    private final PlatformTransactionManager transactionManager;
//    private final DataSource dataSource;
//
//    private int chunk_size = 10;
//
//    @Bean
//    public Step apiMasterStep() {
//        return new StepBuilder("apiMasterStep", jobRepository)
//                .partitioner(apiSlaveStep().getName(), partitioner())
//                .step(apiSlaveStep())
//                .gridSize(3)
//                .taskExecutor(new SimpleAsyncTaskExecutor())
//                .build();
//    }
//
//    @Bean
//    public Step apiSlaveStep(){
//        return new StepBuilder("apiSlaveStep", jobRepository)
//                .<ProductVO, ProductVO>chunk(chunk_size, transactionManager)
//                .reader(null)
//                .processor(null)
//                .writer(null)
//                .build();
//    }
//
//    @Bean
//    public ProductPartitioner  partitioner(){
//        ProductPartitioner partitioner = new ProductPartitioner(this.dataSource);
//        return partitioner;
//    }
//}
