package com.project.springbatch.step6;

import com.project.springbatch.BatchConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.job.builder.JobBuilder;
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
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Arrays;
import java.util.List;


/**
 * Chunk
 */
@Configuration
@RequiredArgsConstructor
public class Chunk3 {

    private final BatchConfig batchConfig;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    private static final Integer CHUNK_SIZE = 2;

    @Bean
    public Job job(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobBuilder("batchJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(step1())
                .next(step2())
                .build();
    }

    @Bean
    public Step step1() {
        return new StepBuilder("step1", jobRepository)
                .<String, String>chunk(CHUNK_SIZE, transactionManager) // SimpleStepBuilder > TaskletStep > ChunkOrientedTasklet 생성
                .reader(new ListItemReader<>(Arrays.asList("item1", "item2", "item3", "item4", "item5")))

                .processor(new ItemProcessor<String, String>() {
                    @Override
                    public String process(String item) throws Exception {
                        return "my_"+ item;
                    }
                })

                .writer(new ItemWriter<String>() {
                    @Override
                    public void write(Chunk<? extends String> chunk) throws Exception {
                        List<? extends String> items = chunk.getItems();
                        items.stream()
                                .forEach(System.out::println);
                    }
                })

                .build();
    }
    @Bean
    public Step step2() {
        return new StepBuilder("step2", jobRepository)
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("start step2 hello world");
                        return RepeatStatus.FINISHED;
                    }
                }, transactionManager)
                .build();
    }
}
