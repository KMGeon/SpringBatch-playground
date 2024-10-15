package com.project.springbatch.a_practice.job.file;

import com.project.springbatch.a_practice.chunk.processor.FileItemProcessor;
import com.project.springbatch.a_practice.domain.Product;
import com.project.springbatch.a_practice.domain.ProductVO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.mybatis.spring.batch.builder.MyBatisBatchItemWriterBuilder;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class FileJobConfiguration {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    @Qualifier("sqlSessionTemplate")
    private final SqlSessionTemplate sqlSessionTemplate;

    private static final int CHUNK_SIZE = 10;


    @Bean
    public Job fileJob() {
        return new JobBuilder("fileJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(fileStep())
                .build();
    }

    @Bean
    public Step fileStep() {
        return new StepBuilder("fileStep", jobRepository)
                .<ProductVO, Product>chunk(CHUNK_SIZE, transactionManager)
                .reader(fileItemReader(null))
                .processor(fileItemProcessor())
                .writer(fileItemWriter())
                .build();
    }

    @Bean
    @StepScope
    public FlatFileItemReader<ProductVO> fileItemReader(@Value("#{jobParameters['requestDate']}") String requestDate) {
        return new FlatFileItemReaderBuilder<ProductVO>()
                .name("flatFile")
                .resource(new ClassPathResource("product_" + requestDate + ".csv"))
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>())
                .targetType(ProductVO.class)
                .linesToSkip(1)
                .delimited().delimiter(",") // 구분자 기호로
                .names("id", "name", "price", "type")
                .build();
    }

    @Bean
    public ItemProcessor<ProductVO, Product> fileItemProcessor() {
        return new FileItemProcessor();
    }

    @Bean
    public MyBatisBatchItemWriter<Product> fileItemWriter() {
        return new MyBatisBatchItemWriterBuilder<Product>()
                .sqlSessionFactory(sqlSessionTemplate.getSqlSessionFactory())
                .statementId("product.insertProduct")
                .itemToParameterConverter(createItemToParameterConverter())
                .assertUpdates(false)
                .build();
    }

    private Converter<Product, Map<String, Object>> createItemToParameterConverter() {
        return item -> {
            Map<String, Object> parameterMap = new HashMap<>();
            System.out.println("item = " + item);
            parameterMap.put("product", item);
            return parameterMap;
        };
    }
}
