//package com.project.springbatch.step4;//package com.project.springbatch.step3;
//
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.job.builder.FlowBuilder;
//import org.springframework.batch.core.job.builder.JobBuilder;
//import org.springframework.batch.core.job.flow.Flow;
//import org.springframework.batch.core.launch.support.RunIdIncrementer;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.core.step.builder.StepBuilder;
//import org.springframework.batch.item.*;
//import org.springframework.batch.item.support.ListItemReader;
//import org.springframework.batch.repeat.RepeatStatus;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import java.util.Arrays;
//
//
///**
// * StepBuilder는 하위 api를 판별하여 각각마다 올바른 Builder를 생성한다.
// */
//@Configuration
//@Component
//public class Step4_1 {
//
//    @Bean
//    Job job(JobRepository jobRepository, Step step1, Step chunkStep, Step step3) {
//        return new JobBuilder("batchJob", jobRepository)
//                .incrementer(new RunIdIncrementer())
//                .start(step1)
//                .start(chunkStep)
////                .next(step3)
//                .build();
//    }
//
//
//    /**
//     *  TaskletStepBuilder > AbstractTaskletStepBuilder에서 하나의 TaskletStep(StepNAME)을 만들고 enhance > StepBuilderHelper에서 jobrepository 설정 , properties를 통해서 설정하고 Tasklet을 만들고 트랜잭션 설정
//     *  > 이후 AbstractTaskletStepBuilder에서 stepOperations = new RepeatTemplate();을 설정하고 TaskletStep을 설정
//     *  > 이후 TaskletStep의 부모인 AbstractStep에서  excute 메서드를 한다. ( SimpleJob이 실행한거 )
//     */
//    @Bean
//    Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
//        return new StepBuilder("step1", jobRepository)// StepBuilder를 생성하여 Step의 이름을 매개변수로 받는다.
//                .tasklet((contribution, chunkContext) -> {//.tasklet((contribution, chunkContext) -> { // TaskletStepBuilder를 반환한다.
//                    System.out.println("step1 was excuted!");
//                    return RepeatStatus.FINISHED;
//                }, transactionManager)
//                .build();
//    }
//
//    @Bean
//    Step chunkStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
//        return new StepBuilder("chunkStep", jobRepository)
//                .<String, String>chunk(10, transactionManager)
//                .reader(new ListItemReader<>(Arrays.asList("item1","item2","item3","item4","item5","item6","item7","item8","item9","item10")))
//                .processor(new ItemProcessor<String, String>() {
//                    @Override
//                    public String process(String item) throws Exception {
//                        return item.toUpperCase();
//                    }
//                })
//                .writer(new ItemWriter<String>() {
//                    @Override
//                    public void write(Chunk<? extends String> chunk) throws Exception {
//                        chunk.forEach(s -> System.out.println(chunk));
//                    }
//                })
//                .build();
//    }
//
//
//    //SimpleStepBuilder > TaskletStep
//    @Bean
//    Step step2(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
//        return new StepBuilder("step2", jobRepository)
//                .<String, String>chunk(10, transactionManager)
//                .reader(new ItemReader<String>() {
//                    @Override
//                    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
//                        System.out.println("11");
//                        return null;
//                    }
//                }).processor(new ItemProcessor<String, String>() {
//                    @Override
//                    public String process(String item) throws Exception {
//                        return null;
//                    }
//                }).writer(new ItemWriter<String>() {
//                    @Override
//                    public void write(Chunk<? extends String> chunk) throws Exception {
//
//                    }
//                })
//                .build();
//    }
//
//
//    //PartitionStepBuilder > partitionStepBuild
//    @Bean
//    public Step step3(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
//        return new StepBuilder("step3", jobRepository)
//                .partitioner(step1(jobRepository, transactionManager))
//                .gridSize(2)
//                .build();
//    }
//
//    // job api > jobStepBuilder > JobStep
//    @Bean
//    public Step step4(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
//        return new StepBuilder("step4", jobRepository)
//                .job(job(jobRepository, step1(jobRepository, transactionManager), step2(jobRepository, transactionManager),step3(jobRepository, transactionManager)))
//                .build();
//    }
//
//    // flow api > FlowStepBuilder > FlowStep
//    @Bean
//    public Step step5(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
//        return new StepBuilder("step5", jobRepository)
//                .flow(flow(jobRepository, transactionManager))
//                .build();
//    }
//
//    @Bean
//    Flow flow(JobRepository jobRepository, PlatformTransactionManager transactionManager){
//        FlowBuilder<Flow> flowBuilder = new FlowBuilder<>("flow");
//        flowBuilder.start(step2(jobRepository, transactionManager))
//                .end();
//        return flowBuilder.build();
//    }
//
//
//
////    @Bean
////    public Step batchStep(JobRepository jobRepository, PlatformTransactionManager transactionManager){
////        return new StepBuilder("batchboj", jobRepository) // StepBuilder를 생성하여 Step의 이름을 매개변수로 받는다.
////                .tasklet((contribution, chunkContext) -> { // TaskletStepBuilder를 반환한다.
////                    System.out.println("Hello World");
////                    return RepeatStatus.FINISHED;
////                }, transactionManager)
////                .startLimit(10) // step의 실행 횟수를 의미하며, 설정한 만큼 실행되고 초과되면 오류 / default로 integer.maxvalue
////                .allowStartIfComplete(true) // step 성공, 실패와 상관없이 항상 step을 실행하는 조건
////                .listener(StepExecution) // step 라이프사이클의 특정 시점에 콜백 받아 StepExecutionListener 설정
////                .build(); // TaskletStep 생성
////    }
//}
