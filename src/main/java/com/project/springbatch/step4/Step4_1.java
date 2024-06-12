package com.project.springbatch.step4;//package com.project.springbatch.step3;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.FlowStepBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.*;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;


/**
 * StepBuilder는 하위 api를 판별하여 각각마다 올바른 Builder를 생성한다.
 */
@Configuration
@Component
public class Step4_1 {

    @Bean
    Job job(JobRepository jobRepository, Step step1, Step step2, Step step3) {
        return new JobBuilder("batchJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(step1)
                .next(step2)
                .next(step3)
                .build();
    }

    /**
     *  TaskletStep
     *
     */
    @Bean
    Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("step1", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("step1 was excuted!");
                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .build();
    }


    //SimpleStepBuilder > TaskletStep
    @Bean
    Step step2(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("step2", jobRepository)
                .<String, String>chunk(3, transactionManager)
                .reader(new ItemReader<String>() {
                    @Override
                    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
                        System.out.println("11");
                        return null;
                    }
                }).processor(new ItemProcessor<String, String>() {
                    @Override
                    public String process(String item) throws Exception {
                        return null;
                    }
                }).writer(new ItemWriter<String>() {
                    @Override
                    public void write(Chunk<? extends String> chunk) throws Exception {

                    }
                })
                .build();
    }


    //PartitionStepBuilder > partitionStepBuild
    @Bean
    public Step step3(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("step3", jobRepository)
                .partitioner(step1(jobRepository, transactionManager))
                .gridSize(2)
                .build();
    }

    // job api > jobStepBuilder > JobStep
    @Bean
    public Step step4(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("step4", jobRepository)
                .job(job(jobRepository, step1(jobRepository, transactionManager), step2(jobRepository, transactionManager),step3(jobRepository, transactionManager)))
                .build();
    }

    // flow api > FlowStepBuilder > FlowStep
    @Bean
    public Step step5(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("step5", jobRepository)
                .flow(flow(jobRepository, transactionManager))
                .build();
    }

    @Bean
    Flow flow(JobRepository jobRepository, PlatformTransactionManager transactionManager){
        FlowBuilder<Flow> flowBuilder = new FlowBuilder<>("flow");
        flowBuilder.start(step2(jobRepository, transactionManager))
                .end();
        return flowBuilder.build();
    }
}
