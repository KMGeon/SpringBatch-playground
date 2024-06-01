//package com.project.springbatch.step2;
//
//import com.zaxxer.hikari.HikariDataSource;
//import org.springframework.batch.core.explore.JobExplorer;
//import org.springframework.batch.core.launch.JobLauncher;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.beans.factory.ObjectProvider;
//import org.springframework.boot.autoconfigure.batch.BatchDataSource;
//import org.springframework.boot.autoconfigure.batch.BatchDataSourceScriptDatabaseInitializer;
//import org.springframework.boot.autoconfigure.batch.BatchProperties;
//import org.springframework.boot.autoconfigure.batch.JobLauncherApplicationRunner;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.util.StringUtils;
//
//import javax.sql.DataSource;
//
//@Configuration
//@EnableConfigurationProperties(BatchProperties.class)
//public class BatchConfig {
//
//    // batchDatasource 사용을 위한 수동 빈 등록
//    @Bean
//    @ConditionalOnMissingBean
//    @ConditionalOnProperty(prefix = "spring.batch.job", name = "enabled", havingValue = "true", matchIfMissing = true)
//    public JobLauncherApplicationRunner jobLauncherApplicationRunner(JobLauncher jobLauncher, JobExplorer jobExplorer,
//                                                                     JobRepository jobRepository, BatchProperties properties) {
//        JobLauncherApplicationRunner runner = new JobLauncherApplicationRunner(jobLauncher, jobExplorer, jobRepository);
//        String jobNames = properties.getJob().getName();
//        if (StringUtils.hasText(jobNames)) {
//            runner.setJobName(jobNames);
//        }
//        return runner;
//    }
//
//    // batchDatasource 사용을 위한 수동 빈 등록
//    @Bean
//    @ConditionalOnMissingBean(BatchDataSourceScriptDatabaseInitializer.class)
//    BatchDataSourceScriptDatabaseInitializer batchDataSourceInitializer(DataSource dataSource,
//                                                                        @BatchDataSource ObjectProvider<DataSource> batchDataSource, BatchProperties properties) {
//        return new BatchDataSourceScriptDatabaseInitializer(batchDataSource.getIfAvailable(() -> dataSource),
//                properties.getJdbc());
//    }
//
//    @Bean
//    @Primary
//    @ConfigurationProperties(prefix = "spring.datasource")
//    public DataSource dataSource() {
//        return DataSourceBuilder.create()
//                .url("jdbc:mysql://localhost:3306/KMG?serverTimezone=Asia/Seoul&characterEncoding=UTF-8")
//                .username("root")
//                .password("1234")
//                .driverClassName("com.mysql.cj.jdbc.Driver")
//                .type(HikariDataSource.class)
//                .build();
//    }
//
//    @Bean
//    public PlatformTransactionManager transactionManager(DataSource dataSource) {
//        return new DataSourceTransactionManager(dataSource);
//    }
//}