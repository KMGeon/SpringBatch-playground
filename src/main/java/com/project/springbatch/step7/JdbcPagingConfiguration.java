//package com.project.springbatch.step7;
//
//import com.project.springbatch.BatchConfig;
//import com.project.springbatch.domain.Customer;
//import lombok.RequiredArgsConstructor;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.job.builder.JobBuilder;
//import org.springframework.batch.core.launch.support.RunIdIncrementer;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.core.step.builder.StepBuilder;
//import org.springframework.batch.item.ItemReader;
//import org.springframework.batch.item.ItemWriter;
//import org.springframework.batch.item.database.Order;
//import org.springframework.batch.item.database.PagingQueryProvider;
//import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
//import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
//import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.core.BeanPropertyRowMapper;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import javax.management.Query;
//import javax.sql.DataSource;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Objects;
//
//@Configuration
//@RequiredArgsConstructor
//public class JdbcBatchItemWriter {
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
//                .<Customer, Customer>chunk(CHUNK_SIZE, transactionManager)
//                .reader(customerItemReader())
//                .writer(customerItemWriter())
//                .build();
//    }
//
//    @Bean
//    public ItemReader<Customer> customerItemReader() throws Exception {
//        Map<String, Object> parameters = new HashMap<>();
//        parameters.put("first_name", "Emma");
//        return new JdbcPagingItemReaderBuilder<Customer>()
//                .name("jdbcPagingReader")
//                .pageSize(10)
//                .dataSource(dataSource)
//                .rowMapper(new BeanPropertyRowMapper<>(Customer.class))
//                .queryProvider(createQueryProvider())
//                .parameterValues(parameters)
//                .build();
//    }
//
//    @Bean
//    public PagingQueryProvider createQueryProvider() throws Exception {
//        var queryProvider  = new SqlPagingQueryProviderFactoryBean();
//        queryProvider.setDataSource(dataSource);
//        queryProvider.setSelectClause("id,first_name,last_name,birthdate");
//        queryProvider.setFromClause("from customer");
//        queryProvider.setWhereClause("where first_name like :first_name");
//
//        Map<String, Order> sortKeys = new HashMap<>();
//        sortKeys.put("id", Order.ASCENDING);
//        queryProvider.setSortKeys(sortKeys);
//
//        return queryProvider.getObject();
//    }
//
//
//    @Bean
//    public ItemWriter<Customer> customerItemWriter(){
//        return items -> {
//            for (Customer item : items) {
//                System.out.println("item = " + item.toString());
//            }
//        };
//    }
//}
