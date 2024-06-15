//package com.project.springbatch.step4;
//
//import org.springframework.batch.core.*;
//import org.springframework.batch.core.job.builder.JobBuilder;
//import org.springframework.batch.core.launch.JobLauncher;
//import org.springframework.batch.core.launch.support.RunIdIncrementer;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.core.scope.context.ChunkContext;
//import org.springframework.batch.core.step.builder.StepBuilder;
//import org.springframework.batch.core.step.job.DefaultJobParametersExtractor;
//import org.springframework.batch.core.step.tasklet.Tasklet;
//import org.springframework.batch.repeat.RepeatStatus;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.transaction.PlatformTransactionManager;
//
//@Configuration
//public class Step_4_JobStep {
//
//    @Bean
//    public Job parentJob(JobRepository jobRepository, Step jobStep, Step step2) {
//        return new JobBuilder("parentJob", jobRepository)
//                .incrementer(new RunIdIncrementer())
//                .start(jobStep)
//                .next(step2)
//                .build();
//    }
//
//    @Bean
//    public Step jobStep(JobRepository jobRepository, JobLauncher jobLauncher, Job childJob){
//        return new StepBuilder("jobStep", jobRepository)
//                .job(childJob)
//                .launcher(jobLauncher)
//                .parametersExtractor(jobParametersExtractor())
//                .listener(new StepExecutionListener() {
//                    @Override
//                    public void beforeStep(StepExecution stepExecution) {
//                        stepExecution.getExecutionContext().putString("name", "user1");
//                    }
//                })
//                .build();
//    }
//
//    @Bean
//    public Job childJob(JobRepository jobRepository, Step step1){
//        return new JobBuilder("childJob", jobRepository)
//                .start(step1)
//                .build();
//    }
//
//    @Bean
//    public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
//        return new StepBuilder("step1", jobRepository)
//                .tasklet(new Tasklet() {
//                    @Override
//                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
//                        System.out.println("step1 hello world");
//                        return RepeatStatus.FINISHED;
//                    }
//                },transactionManager)
//                .build();
//    }
//
//    /**
//     * execution context에 있는 key를 찾는다.
//     *
//     */
//    private DefaultJobParametersExtractor jobParametersExtractor(){
//        DefaultJobParametersExtractor extractor = new DefaultJobParametersExtractor();
//        extractor.setKeys(new String[]{"name"});
//        return extractor;
//    }
//
//    @Bean
//    public Step step2(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
//        return new StepBuilder("step2", jobRepository)
//                .tasklet(new Tasklet() {
//                    @Override
//                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
//                        System.out.println("start step2 hello world");
//                        return RepeatStatus.FINISHED;
//                    }
//                },transactionManager)
//                .build();
//    }
//
//}
