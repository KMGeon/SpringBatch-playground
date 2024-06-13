package com.project.springbatch.step4;//package com.project.springbatch.step3;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Arrays;


/**
 * StepBuilder는 하위 api를 판별하여 각각마다 올바른 Builder를 생성한다.
 */
@Configuration
@Component
public class Step4_2 {

    @Bean
    Job job(JobRepository jobRepository, Step step99) {
        return new JobBuilder("step4_2", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(step99)
                .build();
    }


    /**
     *  TaskletStepBuilder > AbstractTaskletStepBuilder에서 하나의 TaskletStep(StepNAME)을 만들고 enhance > StepBuilderHelper에서 jobrepository 설정 , properties를 통해서 설정하고 Tasklet을 만들고 트랜잭션 설정
     *  > 이후 AbstractTaskletStepBuilder에서 stepOperations = new RepeatTemplate();을 설정하고 TaskletStep을 설정
     *  > 이후 TaskletStep의 부모인 AbstractStep에서  excute 메서드를 한다. ( SimpleJob이 실행한거 )
     */
    @Bean
    Step step99(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("step1", jobRepository)// StepBuilder를 생성하여 Step의 이름을 매개변수로 받는다.
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("asdasdakljfl;asdjk");
                        return RepeatStatus.FINISHED;
                    }
                }, transactionManager)
                .build();
    }


}
