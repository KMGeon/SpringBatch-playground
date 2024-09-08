package com.project.springbatch.step7;

import com.project.springbatch.BatchConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class FlatFilesConfiguration_1 {

    private final BatchConfig batchConfig;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    private static final Integer CHUNK_SIZE = 10;

    @Bean
    public Job job(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobBuilder("batchJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(step1())
                .build();
    }

    /**
     * 배치 사이즈 만큼 파일을 읽는다. 그 후에 ItemWriter에게 값을 전달한다.
     * 지금 10줄씩 일고 값을 전달한다.
     */
    @Bean
    public Step step1() {
        return new StepBuilder("step1", jobRepository)
                .<String, String>chunk(CHUNK_SIZE, transactionManager)
                .reader(itemReader())
                .writer(new ItemWriter<String>() {
                    @Override
                    public void write(Chunk<? extends String> chunk) throws Exception {
                        List<? extends String> items = chunk.getItems();
                        System.out.println("items = " + items);
                        System.out.println("아이템 사이즈 = " + items.size());
                    }
                })

                .build();
    }

//    @Bean
//    public ItemReader itemReader(){
//        FlatFileItemReader<Customer> itemReader = new FlatFileItemReader<>();
//        itemReader.setResource(new ClassPathResource("/customer.csv")); // csv파일 가져오기
//
//        DefaultLineMapper<Customer>lineMapper = new DefaultLineMapper<>();
//        lineMapper.setLineTokenizer(new DelimitedLineTokenizer()); // 기본
//        lineMapper.setFieldSetMapper(new CustomerFieldSetMapper()); // custom
//        itemReader.setLineMapper(lineMapper);
//
//        // header 넘어가기
//        itemReader.setLinesToSkip(1);
//        return itemReader;
//    }

    //                .fieldSetMapper(new CustomerFieldSetMapper()) 이거가     .fieldSetMapper(new BeanWrapperFieldSetMapper<>())
    //                .targetType(Customer.class) 대체 가능
    @Bean
    public ItemReader itemReader(){
        return new FlatFileItemReaderBuilder<Customer>()
                .name("flatFile")
                .resource(new ClassPathResource("/customer.csv"))
//                .fieldSetMapper(new CustomerFieldSetMapper())
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>())
                .targetType(Customer.class)
                .linesToSkip(1)
                .delimited().delimiter(",")
                .names("name","age","year") // 기존에 index에서 name으로도 가져올 수 있다.
                .build();
    }
}
