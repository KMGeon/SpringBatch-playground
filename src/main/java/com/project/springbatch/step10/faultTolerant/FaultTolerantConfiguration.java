//package com.project.springbatch.step10;
//
//import com.project.springbatch.BatchConfig;
//import lombok.RequiredArgsConstructor;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.job.builder.JobBuilder;
//import org.springframework.batch.core.launch.support.RunIdIncrementer;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.core.step.builder.StepBuilder;
//import org.springframework.batch.item.*;
//import org.springframework.batch.repeat.RepeatCallback;
//import org.springframework.batch.repeat.RepeatContext;
//import org.springframework.batch.repeat.RepeatStatus;
//import org.springframework.batch.repeat.exception.SimpleLimitExceptionHandler;
//import org.springframework.batch.repeat.policy.SimpleCompletionPolicy;
//import org.springframework.batch.repeat.support.RepeatTemplate;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import javax.sql.DataSource;
//
//@Configuration
//@RequiredArgsConstructor
//public class FaultTolerantConfiguration {
//
//    private final BatchConfig batchConfig;
//    private final JobRepository jobRepository;
//    private final PlatformTransactionManager transactionManager;
//    private final DataSource dataSource;
//
//    private static final Integer CHUNK_SIZE = 10;
//
//    @Bean
//    public Job job(JobRepository jobRepository, PlatformTransactionManager transactionManager) throws Exception {
//        return new JobBuilder("batchJob", jobRepository)
//                .incrementer(new RunIdIncrementer())
//                .start(step1())
//                .build();
//    }
//
//    @Bean
//    public Step step1() throws Exception {
//        return new StepBuilder("step1", jobRepository)
//                .<String, String>chunk(CHUNK_SIZE, transactionManager)
//                .reader(new ItemReader<String>() {
//                    int i = 0;
//                    @Override
//                    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
//                        i++;
//                        if (i == 1) {
//                            System.out.println("i = " + i);
//                            throw new IllegalArgumentException("i==1 skip exception");
//                        }
//                        System.out.println("i = " + i);
//                        return i > 3 ? null : "item" + i;
//                    }
//                })
//                .processor(new ItemProcessor<String, String>() {
//                    @Override
//                    public String process(String item) throws Exception {
//                        System.out.println("item = " + item);
//                        throw new IllegalStateException("this exception is retry");
////                        return null;
//                    }
//                })
//                .writer(chunk -> System.out.println("chunk = " + chunk))
//                .faultTolerant()
//                .skip(IllegalArgumentException.class)
//                .skipLimit(2)
//                .retry(IllegalStateException.class)
//                .retryLimit(2)
//                .build();
//    }
//}