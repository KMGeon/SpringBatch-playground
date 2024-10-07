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
//import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
//import org.springframework.batch.item.json.JacksonJsonObjectReader;
//import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import javax.sql.DataSource;
//
//@Configuration
//@RequiredArgsConstructor
//public class JdbcCursorConfiguration {
//
//    private final BatchConfig batchConfig;
//    private final JobRepository jobRepository;
//    private final PlatformTransactionManager transactionManager;
//    private final DataSource dataSource;
//
//    private static final Integer CHUNK_SIZE = 3;
//
//    @Bean
//    public Job job(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
//        return new JobBuilder("batchJob", jobRepository)
//                .incrementer(new RunIdIncrementer())
//                .start(step1())
//                .build();
//    }
//
//    @Bean
//    public Step step1() {
//        return new StepBuilder("step1", jobRepository)
//                .<Customer, Customer>chunk(CHUNK_SIZE, transactionManager)
//                .reader(customerItemReader())
//                .writer(customerItemWriter())
//                .build();
//    }
//
//    @Bean
//    public ItemReader<Customer> customerItemReader() {
//        return new JdbcCursorItemReaderBuilder<Customer>()
//                .name("jsonReader")
//                .fetchSize(CHUNK_SIZE)
//                .sql("select id, birthdate, first_name, last_name from customer where first_name like ? order by last_name, first_name")
//                .beanRowMapper(Customer.class)
//                .queryArguments("Emma")
//                .dataSource(dataSource)
//                .build();
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
