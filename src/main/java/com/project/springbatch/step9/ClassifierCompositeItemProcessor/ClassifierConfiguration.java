//package com.project.springbatch.step9.ClassifierCompositeItemProcessor;//package com.project.springbatch.step8;//package com.project.springbatch.step7;
//
//import com.project.springbatch.BatchConfig;
//import com.project.springbatch.step9.CompositeItemProcessor.CustomItemProcessor;
//import com.project.springbatch.step9.CompositeItemProcessor.CustomItemProcessor2;
//import com.project.springbatch.step9.CompositeItemProcessor.CustomItemProcessor3;
//import lombok.RequiredArgsConstructor;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.job.builder.JobBuilder;
//import org.springframework.batch.core.launch.support.RunIdIncrementer;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.core.step.builder.StepBuilder;
//import org.springframework.batch.item.*;
//import org.springframework.batch.item.support.builder.CompositeItemProcessorBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import javax.sql.DataSource;
//import java.util.ArrayList;
//import java.util.List;
//
//@Configuration
//@RequiredArgsConstructor
//public class ClassifierConfiguration {
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
//                .<String, String>chunk(10, transactionManager)
//                .reader(new ItemReader<String>() {
//                    int i=0;
//                    @Override
//                    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
//                        i++;
//                        return  i> 10 ? null : "item";
//                    }
//                }).processor(testCustomItemProcessor())
//                .writer(chunk -> {
//                    System.out.println("chunk = " + chunk);
//                })
//                .build();
//    }
//
//    @Bean
//    public ItemProcessor<? super String, String> testCustomItemProcessor() {
//        List itemProcessor = new ArrayList<>();
//        itemProcessor.add(new CustomItemProcessor());
//        itemProcessor.add(new CustomItemProcessor2());
//        itemProcessor.add(new CustomItemProcessor3());
//
//        return new CompositeItemProcessorBuilder<>()
//                .delegates(itemProcessor)
//                .build();
//
//    }
//}
