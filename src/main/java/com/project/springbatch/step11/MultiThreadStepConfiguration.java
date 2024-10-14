//package com.project.springbatch.step11;
//
//import com.project.springbatch.web.model.CustomerVO;
//import lombok.RequiredArgsConstructor;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.mybatis.spring.batch.MyBatisBatchItemWriter;
//import org.mybatis.spring.batch.MyBatisPagingItemReader;
//import org.mybatis.spring.batch.builder.MyBatisBatchItemWriterBuilder;
//import org.mybatis.spring.batch.builder.MyBatisPagingItemReaderBuilder;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.job.builder.JobBuilder;
//import org.springframework.batch.core.launch.support.RunIdIncrementer;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.core.step.builder.StepBuilder;
//import org.springframework.batch.integration.async.AsyncItemProcessor;
//import org.springframework.batch.integration.async.AsyncItemWriter;
//import org.springframework.batch.item.ItemProcessor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.convert.converter.Converter;
//import org.springframework.core.task.SimpleAsyncTaskExecutor;
//import org.springframework.core.task.TaskExecutor;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import javax.sql.DataSource;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.concurrent.Future;
//
///**
// * Job 실행 시간: 7초 228밀리초
// * Job 실행 시간: 2초 703밀리초
// *
// * 동기에 했을 때 1개가 10번을 처리하게 되는데
// * 비동기로 처리하게 되면 4개가 처리해서 빠르게 처리한다.
// *
// * 비동기에서 Thread의 순서대로 데이터를 순차적으로 읽는 것은 아니다.
// * thread 1 : item 1
// * thread 2 : item 2
// * thread 3 : item 3 --> x
// *
// * cpu를 점유하는 thread가 먼저 읽는다.
// *
// *
// * @Bean
// *     public TaskExecutor taskExecutor() {
// *         ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
// *         taskExecutor.setCorePoolSize(2);// 기본적으로 생성하는 개수
// *         taskExecutor.setMaxPoolSize(8); // 최대 생성하는 개수
// *         taskExecutor.setThreadNamePrefix("async-thread");
// *         return taskExecutor;
// *     }
// *
// *   - 이렇게 2개로 했는데 thread 2개만 일을 하고 3개로 늘어나지 않는 이유는 다음과 같다.
// */
//@Configuration
//@RequiredArgsConstructor
//public class MultiThreadStepConfiguration {
//    private final JobRepository jobRepository;
//    private final PlatformTransactionManager transactionManager;
//    private final DataSource dataSource;
//
//    @Autowired
//    @Qualifier("sqlSessionTemplate")
//    private SqlSessionTemplate sqlSessionTemplate;
//
//    private static final int CHUNK_SIZE = 100;
//
//    @Bean
//    public Job job(JobRepository jobRepository, PlatformTransactionManager transactionManager) throws Exception {
//        return new JobBuilder("batchJob", jobRepository)
//                .incrementer(new RunIdIncrementer())
//                .start(mybatisCursorStep())
//                .listener(new StepWatchJobListener())
//                .build();
//    }
//
//    /**
//     * taskExecutor을 설정하지 않으면 동기적으로 처리한다.
//     */
//    @Bean
//    public Step mybatisCursorStep() {
//        return new StepBuilder("step1", jobRepository)
//                .<CustomerVO, CustomerVO>chunk(CHUNK_SIZE, transactionManager)
//                .reader(mybatisPagingReader())
//                .listener(new CustomItemReadListener())
//                .processor((ItemProcessor<? super CustomerVO, ? extends CustomerVO>) item -> item)
//                .listener(new CustomItemProcessorListener())
//                .writer(customerItemWriter())
//                .listener(new CustomItemWriterListener())
////                .taskExecutor(new SimpleAsyncTaskExecutor())
//                .taskExecutor(taskExecutor())
//                .build();
//    }
//
//    @Bean
//    public TaskExecutor taskExecutor() {
//        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
//        taskExecutor.setCorePoolSize(3);// 기본적으로 생성하는 개수
//        taskExecutor.setMaxPoolSize(8); // 최대 생성하는 개수
//        taskExecutor.setThreadNamePrefix("async-thread");
//        return taskExecutor;
//    }
//
//
//    @Bean
//    public MyBatisPagingItemReader<CustomerVO> mybatisPagingReader() {
//        Map<String, Object> parameterValues = new HashMap<>();
//        return new MyBatisPagingItemReaderBuilder<CustomerVO>()
//                .sqlSessionFactory(sqlSessionTemplate.getSqlSessionFactory())
//                .queryId("customer.findCustomerPaging")
//                .parameterValues(parameterValues)
//                .pageSize(CHUNK_SIZE)
//                .build();
//    }
//
//
//    @Bean
//    public ItemProcessor<CustomerVO, CustomerVO> customItemProcessor() {
//        return item -> {
//            return CustomerVO.builder()
//                    .id(item.getId())
//                    .first_name(item.getFirst_name())
//                    .last_name(item.getLast_name())
//                    .birthdate(item.getBirthdate())
//                    .build();
//        };
//    }
//
//    @Bean
//    public MyBatisBatchItemWriter<CustomerVO> customerItemWriter() {
//        return new MyBatisBatchItemWriterBuilder<CustomerVO>()
//                .sqlSessionFactory(sqlSessionTemplate.getSqlSessionFactory())
//                .statementId("customer.insertCustomer2")
//                .itemToParameterConverter(createItemToParameterConverter())
//                .assertUpdates(false)
//                .build();
//    }
//
//    private Converter<CustomerVO, Map<String, Object>> createItemToParameterConverter() {
//        return item -> {
//            Map<String, Object> parameterMap = new HashMap<>();
//            parameterMap.put("customer", item);
//            return parameterMap;
//        };
//    }
//}