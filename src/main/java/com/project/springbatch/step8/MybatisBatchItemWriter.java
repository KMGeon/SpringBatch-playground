//package com.project.springbatch.step8;
//
//import com.project.springbatch.BatchConfig;
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
//import org.springframework.batch.item.ItemWriter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import javax.sql.DataSource;
//import java.util.HashMap;
//import java.util.Map;
//import org.springframework.core.convert.converter.Converter;
//
//@Configuration
//@RequiredArgsConstructor
//public class MybatisBatchItemWriter {
//
//    private final BatchConfig batchConfig;
//    private final JobRepository jobRepository;
//    private final PlatformTransactionManager transactionManager;
//    private final DataSource dataSource;
//
//    @Autowired
//    @Qualifier("sqlSessionTemplate")
//    private SqlSessionTemplate sqlSessionTemplate;
//
//    private static final int CHUNK_SIZE = 10;
//
//    @Bean
//    public Job mybatisCursorJob() throws Exception {
//        return new JobBuilder("batchJob", jobRepository)
//                .incrementer(new RunIdIncrementer())
//                .start(mybatisCursorStep())
//                .build();
//    }
//
//    @Bean
//    public Step mybatisCursorStep() throws Exception {
//        return new StepBuilder("mybatisCursorStep", jobRepository)
//                .<CustomerVO, CustomerVO>chunk(CHUNK_SIZE, transactionManager)
//                .reader(mybatisPagingReader())
//                .writer(customerItemWriter())
//                .build();
//    }
//
//    @Bean
//    public MyBatisPagingItemReader<CustomerVO> mybatisPagingReader() {
//        Map<String, Object> parameterValues = new HashMap<>();
//        /**
//         * #{_skiprows}와 #{_pagesize}는 MyBatisPagingItemReader가 자동으로 관리하는 특별한 파라미터입니다.
//         * 개발자가 직접 이 값들을 설정할 필요가 없다. 대신, MyBatisPagingItemReader를 구성할 때 pageSize를 설정하면 됩니다.
//         * 작동 방식은 다음과 같습니다
//         *
//         * pageSize: Java 코드에서 설정한 이 값이 SQL 쿼리의 #{_pagesize}에 자동으로 매핑됩니다.
//         * _skiprows: 이 값은 (현재 페이지 번호 - 1) * pageSize로 자동 계산되어 SQL 쿼리에 적용됩니다.
//         */
//        return new MyBatisPagingItemReaderBuilder<CustomerVO>()
//                .sqlSessionFactory(sqlSessionTemplate.getSqlSessionFactory())
//                .queryId("customer.findCustomerPaging")
//                .parameterValues(parameterValues)
//                .pageSize(CHUNK_SIZE)
//                .build();
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