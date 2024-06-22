//package com.project.springbatch.step6;
//
//import com.project.springbatch.BatchConfig;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import lombok.RequiredArgsConstructor;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.job.builder.JobBuilder;
//import org.springframework.batch.core.launch.support.RunIdIncrementer;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.core.step.builder.StepBuilder;
//import org.springframework.batch.item.*;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//
//
//@Configuration
//@RequiredArgsConstructor
//public class Chunk2 {
//
//    private final BatchConfig batchConfig;
//    private final JobRepository jobRepository;
//    private final PlatformTransactionManager transactionManager;
//    private final CustomItemReader customItemReader;
//    private final CustomItemProcessor customItemProcessor;
//    private final CustomWriter customWriter;
//
//    private static final Integer CHUNK_SIZE = 2;
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
//                .reader(new CustomItemReader(Arrays.asList(new Customer("customer1"),
//                        new Customer("customer2"),
//                        new Customer("customer3"),
//                        new Customer("customer4"),
//                        new Customer("customer5"))))
//                .processor(customItemProcessor)
//                .writer(customWriter)
//                .build();
//    }
//
//}
//
///**
// * custom
// */
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//class Customer {
//    private String name;
//}
//
//@Component
//class CustomItemReader implements ItemReader<Customer> {
//
//    private List<Customer> customers;
//
//    public CustomItemReader(List<Customer> customers) {
//        this.customers = new ArrayList<>(customers);
//    }
//
//    @Override
//    public Customer read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
//        if (!customers.isEmpty()){
//            return customers.remove(0);
//        }
//
//        return null;
//    }
//}
//
//@Component
//class CustomItemProcessor implements ItemProcessor<Customer, Customer>{
//
//    @Override
//    public Customer process(Customer item) throws Exception {
//        item.setName(item.getName().toUpperCase());
//        return item;
//    }
//}
//
//@Component
//class CustomWriter implements ItemWriter<Customer>{
//
//    @Override
//    public void write(Chunk<? extends Customer> chunk) throws Exception {
//        chunk.getItems().forEach(System.out::println);
//    }
//}