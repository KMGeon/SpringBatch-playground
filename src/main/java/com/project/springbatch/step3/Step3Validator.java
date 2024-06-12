//package com.project.springbatch.step3;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.batch.core.*;
//import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
//import org.springframework.batch.core.job.DefaultJobParametersValidator;
//import org.springframework.batch.core.job.builder.JobBuilder;
//import org.springframework.batch.core.launch.JobLauncher;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.core.step.builder.StepBuilder;
//import org.springframework.batch.repeat.RepeatStatus;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import java.util.UUID;
//
//@Configuration
//@RequiredArgsConstructor
//@EnableBatchProcessing
//public class Step3Validator {
//
////    private final CustomJobParamValidator customJobParamValidator;
//
//    @Bean
//    ApplicationRunner runner(JobLauncher jobLauncher, Job job) {
//        return new ApplicationRunner() {
//            @Override
//            public void run(ApplicationArguments args) throws Exception {
//                JobParameters jobParameters = new JobParametersBuilder()
//                        .addString("name", UUID.randomUUID().toString())
//                        .toJobParameters();
//
//                System.out.println("Running job with parameters: " + jobParameters);
//
//                jobLauncher.run(job, jobParameters);
//            }
//        };
//    }
//
//    /**
//     * 	public DefaultJobParametersValidator(String[] requiredKeys, String[] optionalKeys)
//     */
//    @Bean
//    Job job(JobRepository jobRepository, Step step1, Step step2) {
//        return new JobBuilder("job1", jobRepository)
//                .start(step1)
//                .next(step2)
////                .validator(customJobParamValidator)
//                .validator(new DefaultJobParametersValidator(new String[]{"name","date"}, new String[]{"count"}))
//                .build();
//    }
//
//    @Bean
//    Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
//        return new StepBuilder("step1", jobRepository)
//                .tasklet((contribution, chunkContext) -> {
//                    System.out.println("step1 was executed!");
//                    return RepeatStatus.FINISHED;
//                }, transactionManager)
//                .build();
//    }
//
//    @Bean
//    Step step2(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
//        return new StepBuilder("step2", jobRepository)
//                .tasklet((contribution, chunkContext) -> {
//                    System.out.println("step2 was executed!");
//                    return RepeatStatus.FINISHED;
//                }, transactionManager)
//                .build();
//    }
//}
////
////@Component
////class CustomJobParamValidator implements JobParametersValidator {
////
////    @Override
////    public void validate(JobParameters parameters) throws JobParametersInvalidException {
////        System.out.println("==============Job Params Start =========================");
////
////        System.out.println("parameters = " + parameters);
////        if (parameters.getString("name") == null) {
////            throw new JobParametersInvalidException("name parameter is null");
////        }
////
////        System.out.println("==============Job Params End =========================");
////    }
////}
