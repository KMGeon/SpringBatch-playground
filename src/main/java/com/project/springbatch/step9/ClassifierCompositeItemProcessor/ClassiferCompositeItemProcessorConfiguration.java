//package com.project.springbatch.step9.ClassifierCompositeItemProcessor;
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
//import org.springframework.batch.item.support.builder.ClassifierCompositeItemProcessorBuilder;
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
//import java.util.HashMap;
//import java.util.Map;
//
//@Configuration
//@RequiredArgsConstructor
//public class ClassiferCompositeItemProcessorConfiguration {
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
//                .<ProcessorInfo, ProcessorInfo>chunk(10, transactionManager)
//                .reader(new ItemReader<ProcessorInfo>() {
//                    int i = 0;
//                    @Override
//                    public ProcessorInfo read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
//                        i++;
//
//                        ProcessorInfo processorInfo = ProcessorInfo.builder()
//                                .id(i)
//                                .build();
//
//                        return  i> 3 ? null : processorInfo;
//                    }
//                })
//                .processor(customItemProcessor())
//                .writer(chunk -> {
//                    System.out.println("chunk = " + chunk);
//                })
//                .build();
//    }
//
//
//    @Bean
//    public ItemProcessor<ProcessorInfo, ProcessorInfo> customItemProcessor() {
//        ClassifierCompositeItemProcessorBuilder<ProcessorInfo, ProcessorInfo> processorBuilder = new ClassifierCompositeItemProcessorBuilder<>();
//
//        ProcessorClassifier<ProcessorInfo, ItemProcessor<?, ? extends ProcessorInfo>> classifier = new ProcessorClassifier<>();
//
//        Map<Integer, ItemProcessor<ProcessorInfo, ProcessorInfo>> processorMap = new HashMap<>();
//        processorMap.put(1, new CustomItemProcessor1());
//        processorMap.put(2, new CustomItemProcessor2());
//        processorMap.put(3, new CustomItemProcessor3());
//
//        classifier.setProcessorMap(processorMap);
//        processorBuilder.classifier(classifier);
//
//        return processorBuilder.build();
//    }
//}