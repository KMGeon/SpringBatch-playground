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
//import org.springframework.batch.repeat.support.RepeatTemplate;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import javax.sql.DataSource;
//
//@Configuration
//@RequiredArgsConstructor
//public class RepeatConfiguration {
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
//
//
///**
// * SimpleCompletionPolicy
// */
////        return new StepBuilder("step1", jobRepository)
////                .<String, String>chunk(10, transactionManager)
////                .reader(new ItemReader<String>() {
////                    int i = 0;
////                    @Override
////                    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
////                        i++;
////                        System.out.println("itemReader = " + i);
////                        return i > 3 ? null : "item" + i;
////                    }
////                })
////                .processor(new ItemProcessor<String, String>() {
////                    final RepeatTemplate template = new RepeatTemplate();
////                    int i=0;
////                    @Override
////                    public String process(String item) throws Exception {
////
////                        template.setCompletionPolicy(new SimpleCompletionPolicy(2));
////                        template.iterate(new RepeatCallback() {
////                            @Override
////                            public RepeatStatus doInIteration(RepeatContext context) throws Exception {
////                                i++;
////                                System.out.println("repeatTemplate test");
////                                System.out.println("i = " + i);
////                                return RepeatStatus.CONTINUABLE;
////                            }
////                        });
////                        System.out.println(" ============================================================ ");
////                        return item;
////                    }
////                })
////                .writer(chunk -> System.out.println("chunk = " + chunk))
////                .build();
////    }
//
//
////        return new StepBuilder("step1", jobRepository)
////                .<String, String>chunk(10, transactionManager)
////                .reader(new ItemReader<String>() {
////                    int i = 0;
////                    @Override
////                    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
////                        i++;
////                        return i > 3 ? null : "item" + i;
////                    }
////                })
////                .processor(new ItemProcessor<String, String>() {
////                    final RepeatTemplate template = new RepeatTemplate();
////                    @Override
////                    public String process(String item) throws Exception {
////
////                        /**
////                         * 2개의 정책이 있으면 맨 마지막에 선언된 정책을 사용한다.
////                         * 만약에 2개의 정책을 복합적으로 사용하고 싶으면
////                         */
//////                        template.setCompletionPolicy(new SimpleCompletionPolicy(CHUNK_SIZE));
//////                        template.setCompletionPolicy(new TimeoutTerminationPolicy(3000)); // 3초
////
////                        CompositeCompletionPolicy policy = new CompositeCompletionPolicy();
////
////                        /**
////                         * 여기서 2개의 정책 중에서 1개라도 만족하면 (or조건) 빠져나온다.
////                         */
////                        CompletionPolicy[] completionPolicies = {
////                                new SimpleCompletionPolicy(2),
////                                new TimeoutTerminationPolicy(3000),
////                        };
////
////                        policy.setPolicies(completionPolicies);
////                        template.setCompletionPolicy(policy);
////
////                        template.iterate(new RepeatCallback() {
////                            @Override
////                            public RepeatStatus doInIteration(RepeatContext context) throws Exception {
////
////                                System.out.println("repeatTemplate test");
////                                return RepeatStatus.CONTINUABLE;
////                            }
////                        });
////                        System.out.println(" ============================================================ ");
////                        return item;
////                    }
////                })
////                .writer(chunk -> System.out.println("chunk = " + chunk))
////                .build();
////    }
//
//        /**
//         * exception
//         */
//        return new StepBuilder("step1", jobRepository)
//                .<String, String>chunk(10, transactionManager)
//                .reader(new ItemReader<String>() {
//                    int i = 0;
//                    @Override
//                    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
//                        i++;
//                        return i > 3 ? null : "item" + i;
//                    }
//                })
//                .processor(new ItemProcessor<String, String>() {
//                    final RepeatTemplate template = new RepeatTemplate();
//                    int i =0;
//                    @Override
//                    public String process(String item) throws Exception {
//
//                        // processor가 new SimpleLimitException을 하면 계속 새로운 객체를 만들어서 동작하지 않는다.
//                        // exception을 빈으로 만들어서 하나의 빈에서 처리되게 만들어야 한다.
//                        template.setExceptionHandler(simpleLimitExceptionHandler());
//
//                        template.iterate(new RepeatCallback() {
//                            @Override
//                            public RepeatStatus doInIteration(RepeatContext context) throws Exception {
//                                i++;
//                                System.out.println("repeatTemplate test "+ i);
//
//                                throw new RuntimeException("exception");
//                            }
//                        });
//                        System.out.println(" ============================================================ ");
//                        return item;
//                    }
//                })
//                .writer(chunk -> System.out.println("chunk = " + chunk))
//                .build();
//    }
//
//    @Bean
//    public SimpleLimitExceptionHandler simpleLimitExceptionHandler(){
//        return new SimpleLimitExceptionHandler(2); // count보다 큰 경우 멈추기 때문에 총 3번이 발생한다.
//    }
//}