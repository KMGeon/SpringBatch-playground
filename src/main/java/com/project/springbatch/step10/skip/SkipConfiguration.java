//package com.project.springbatch.step10.skip;
//
//import com.project.springbatch.BatchConfig;
//import lombok.RequiredArgsConstructor;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.job.builder.JobBuilder;
//import org.springframework.batch.core.launch.support.RunIdIncrementer;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.core.step.builder.StepBuilder;
//import org.springframework.batch.core.step.skip.LimitCheckingItemSkipPolicy;
//import org.springframework.batch.core.step.skip.SkipPolicy;
//import org.springframework.batch.item.*;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import javax.sql.DataSource;
//import java.util.HashMap;
//import java.util.Map;
//
//@Configuration
//@RequiredArgsConstructor
//public class SkipConfiguration {
//
//    private final BatchConfig batchConfig;
//    private final JobRepository jobRepository;
//    private final PlatformTransactionManager transactionManager;
//    private final DataSource dataSource;
//
//    private static final Integer CHUNK_SIZE = 5;
//
//    @Bean
//    public Job job(JobRepository jobRepository, PlatformTransactionManager transactionManager) throws Exception {
//        return new JobBuilder("batchJob", jobRepository)
//                .incrementer(new RunIdIncrementer())
//                .start(step1())
//                .build();
//    }
//
//    /**
//     * ItemReader : 1
//     * ItemReader : 2
//     * ItemReader : 4
//     * ItemReader : 5
//     * ItemReader : 6
//     * ItemProcess : 1
//     * ItemProcess : 2
//     * ItemProcess : 4
//     * ItemProcess : 5
//     * ItemProcess : 6
//     * =================================
//     * ItemProcess : 1
//     * ItemProcess : 2
//     * ItemProcess : 4
//     * ItemProcess : 5
//     *
//     * - 여기서 itemProcess에서 6이 찍힌다. 여기서 다시 read로 넘어간다.
//     * read에서 6까지 보내는데 itemProcess는 6을 가지고 있기 때문에 6을 처리하지 않는다.
//     * 그래서 1~5까지 로그에 나온다.
//     */
//
//    /**
//     * ItemReader : 11
//     * ItemProcess : 7
//     * 2024-10-10 15:01:40 [ERROR] [] [] Encountered an error executing step step1 in job batchJob
//     * org.springframework.batch.core.step.skip.SkipLimitExceededException: Skip limit of '2' exceeded
//     * 	at org.springframework.batch.core.step.skip.LimitCheckingItemSkipPolicy.shouldSkip(LimitCheckingItemSkipPolicy.java:125)
//     * 	이렇게 에러가 발생하면 새로운 청크를 생성한다.
//     * 	이후 로그에 대해서는 보여주기는 하지만 처리가 되지는 않는다.
//     *
//     */
//    @Bean
//    public Step step1() throws Exception {
//        return new StepBuilder("step1", jobRepository)
//                .<String, String>chunk(CHUNK_SIZE, transactionManager)
//                .reader(new ItemReader<String>() {
//                    int i = 0;
//
//                    @Override
//                    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
//                        i++;
//                        if (i == 3) throw new SkippableException("skip exception");
//                        System.out.println("ItemReader : " + i);
//                        return i > 20 ? null : String.valueOf(i);
//                    }
//                })
//                .processor(itemProcessor())
//                .writer(itemWriter())
//                .faultTolerant()
////                .skip(SkippableException.class)
////                .noSkip(NoSkippableException.class)
////                .skipLimit(2)
//                .skipPolicy(limitCheckingItemSkipPolicy())
//                .build();
//    }
//
//    @Bean
//    public SkipPolicy limitCheckingItemSkipPolicy() {
//        Map<Class<? extends Throwable>, Boolean> exceptionClass = new HashMap<>();
//        exceptionClass.put(SkippableException.class, true);
//
//        return new LimitCheckingItemSkipPolicy(3, exceptionClass);
//    }
//
//    @Bean
//    public ItemWriter<? super String> itemWriter() {
//        return new SkipItemWriter();
//    }
//
//    @Bean
//    ItemProcessor<? super String, String> itemProcessor() {
//        return new SkipItemProcessor();
//    }
//
//}
//
