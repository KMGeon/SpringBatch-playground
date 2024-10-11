package com.project.springbatch.step10.retry.template;

import com.project.springbatch.BatchConfig;
import com.project.springbatch.step10.retry.api.RetryItemProcessor;
import com.project.springbatch.step10.retry.api.RetryTestExcedption;
import com.project.springbatch.web.model.CustomerVO;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;

@Configuration
@RequiredArgsConstructor
public class RetryTemplateConfiguration {

    private final BatchConfig batchConfig;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final DataSource dataSource;

    private static final Integer CHUNK_SIZE = 5;

    @Bean
    public Job job(JobRepository jobRepository, PlatformTransactionManager transactionManager) throws Exception {
        return new JobBuilder("batchJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(step1())
                .build();
    }

    @Bean
    public Step step1() throws Exception {
        return new StepBuilder("step1", jobRepository)
                .<String, CustomerVO>chunk(CHUNK_SIZE, transactionManager)
                .reader(reader())
                .processor(itemProcessor2())
                .writer(item -> item.forEach(s -> System.out.println("s = " + s)))
                .faultTolerant()
                .build();
    }

    @Bean
    public ItemProcessor<String, CustomerVO> itemProcessor2() {
//        return new RetryItemProcessor2();
        return null;
    }

    @Bean
    public ListItemReader<String> reader(){
        var list = new ArrayList<String>();
        for (int i=0; i< 30; i++){
            list.add(String.valueOf(i));
        }
        return new ListItemReader<>(list);
    }

    @Bean
    public RetryPolicy retryPolicy() {
        HashMap<Class<? extends Throwable>, Boolean> map = new HashMap<>();
        map.put(RetryTestExcedption.class, true);
        return new SimpleRetryPolicy(2, map);
    }

    @Bean
    public RetryTemplate retryTemplate(){
        HashMap<Class<? extends Throwable>, Boolean> map = new HashMap<>();
        map.put(RetryTestExcedption.class, true);

        FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
        backOffPolicy.setBackOffPeriod(1000);

        SimpleRetryPolicy policy = new SimpleRetryPolicy(2, map);
        RetryTemplate retryTemplate = new RetryTemplate();
        retryTemplate.setRetryPolicy(policy);
        retryTemplate.setBackOffPolicy(backOffPolicy);
        return retryTemplate;
    }

}

