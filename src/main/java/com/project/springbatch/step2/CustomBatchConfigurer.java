//package com.project.springbatch.step2;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.batch.core.configuration.BatchConfigurationException;
//import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.sql.DataSource;
//
//@Configuration
//@RequiredArgsConstructor
//public class CustomBatchConfigurer extends DefaultBatchConfiguration {
//
//    private final DataSource dataSource;
//
//    @Override
//    public JobRepository jobRepository() throws BatchConfigurationException {
//
//        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
//        factory.setDataSource(dataSource);
//        factory.setTransactionManager(getTransactionManager());
//        return super.jobRepository();
//    }
//}
