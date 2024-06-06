//package com.project.springbatch.step2;
//
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.JobParameters;
//import org.springframework.batch.core.JobParametersBuilder;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
//import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
//import org.springframework.batch.core.job.builder.JobBuilder;
//import org.springframework.batch.core.launch.JobLauncher;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.core.step.builder.StepBuilder;
//import org.springframework.batch.core.step.tasklet.Tasklet;
//import org.springframework.batch.repeat.RepeatStatus;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import java.util.Map;
//import java.util.UUID;
//
//@Configuration
////@EnableBatchProcessing
//public class Batch2{
//
//    @Bean
//    Job job(JobRepository jobRepository, Step step1, Step step2) {
//        return new JobBuilder("job11", jobRepository)
//                .start(step1)
//                .next(step2)
//                .build();
//    }
//
//    @Bean
//    Step step2(JobRepository jobRepository, Tasklet tasklet2, PlatformTransactionManager transactionManager) {
//        return new StepBuilder("step2", jobRepository)
//                .tasklet(tasklet2, transactionManager)
//                .build();
//    }
//
//
//    @Bean
//    Step step1(JobRepository jobRepository, Tasklet tasklet1, PlatformTransactionManager transactionManager) {
//        return new StepBuilder("step1", jobRepository)
//                .tasklet(new CusteomTasklet(), transactionManager)
//                .build();
//    }
//
//    @Bean
//    Tasklet tasklet1() {
//        return (contribution, chunkContext) -> {
//            System.out.println("step1 was excuted!");
//            return RepeatStatus.FINISHED;
//        };
//    }
//
//    @Bean
//    Tasklet tasklet2() {
//        return (contribution, chunkContext) -> {
//
//            //linkedHashMap
//            // jobparameters 객체 안에서 데이터가 저장이 되어져 있다.
////            JobParameters jobParameters = contribution.getStepExecution().getJobExecution().getJobParameters();
////            System.out.println("UUID = " + jobParameters.getString("UUID"));
////            System.out.println("string = " + jobParameters.getString("string"));
////            System.out.println("localdate = " + jobParameters.getLocalDate("localdate"));
////            System.out.println("date = " + jobParameters.getDate("date"));
////            System.out.println("localtime = " + jobParameters.getLocalDateTime("LocalDateTime"));
////            System.out.println("double = " + jobParameters.getDouble("double"));
////
////
////            //map
////            // map에 바로 저장한 값을 주기 때문에 jobparameters 객체를 통해서 데이터를 가져올 필요가 없다.
////            // jobparameters의 객체를 변경을 하게 된다면 그 시점의 값만 확인할 수 있다.
////            //chunkContext.getStepContext().getStepExecution().getJobExecution().getJobParameters();
////            Map<String, Object> chunck = chunkContext.getStepContext().getJobParameters();
////            System.out.println("UUID = " + chunck.get("UUID"));
////            System.out.println("string = " + chunck.get("string"));
////            System.out.println("localdate = " + chunck.get("localdate"));
////            System.out.println("date = " + chunck.get("date"));
////            System.out.println("localtime = " + chunck.get("LocalDateTime"));
////            System.out.println("double = " + chunck.get("double"));
//
//            System.out.println("step2 was excuted!");
//            return RepeatStatus.FINISHED;
//        };
//    }
//
//}
