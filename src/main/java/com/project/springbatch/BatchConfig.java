package com.project.springbatch;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.batch.BatchDataSource;
import org.springframework.boot.autoconfigure.batch.BatchDataSourceScriptDatabaseInitializer;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.boot.autoconfigure.batch.JobLauncherApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;

@Configuration
@EnableConfigurationProperties(BatchProperties.class)
// @EnableBatchProcessing과 DefaultBatchConfiguration를 함께 사용하면 안 된다.
public class BatchConfig{


    /**
     * 조건부로 빈을 등록을 한다.
     * @ConditionalOnMissingBean : 이 어노테이션은 해당 타입의 빈이 이미 존재하지 않을 때만 빈을 등록하도록 한다..
     * 즉, JobLauncherApplicationRunner 타입의 빈이 이미 애플리케이션 컨텍스트에 존재하지 않을 때만 이 메서드를 통해 빈이 등록됩니다.
     *
     * @ConditionalOnProperty(prefix = "spring.batch.job", name = "enabled", havingValue = "true", matchIfMissing = true):
     *
     * prefix는 속성의 접두사를 지정 >> "spring.batch.job"으로 설정
     * name은 속성의 이름 > "enabled"로 설정
     * havingValue는 속성의 값 > "true"로 설정
     * {
     *      matchIfMissing은 속성이 존재하지 않을 때의 동작을 지정
     *      여기서는 true로 설정되어 있어, 속성이 존재하지 않으면 havingValue의 값으로 간주
     * }
     *
     * -----------------
     * 즉. 1. JobLauncherApplicationRunner가 빈이 컨텍스트에 없어야하고
     *     2. application.yml 에서 spring.batch.job.enabled 속성이 true이거나 설정되지 않았을 때 등록됨
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "spring.batch.job", name = "enabled", havingValue = "true", matchIfMissing = true)
    public JobLauncherApplicationRunner jobLauncherApplicationRunner(JobLauncher jobLauncher, JobExplorer jobExplorer,
                                                                     JobRepository jobRepository, BatchProperties properties) {
        /**
         * 1. setJobName을 초기화
         * 2. Collection에 Job을 담아서 빈값이 아니면 실행하게 만들었다.
         */
        JobLauncherApplicationRunner runner = new JobLauncherApplicationRunner(jobLauncher, jobExplorer, jobRepository);
        String jobNames = properties.getJob().getName(); // jobName을 가져온다. ( 여기서 가져오는 jobName은 yml부분 )
        if (StringUtils.hasText(jobNames)) {
            runner.setJobName(jobNames);
        }
        return runner;
    }

    // BatchDataSourceScriptDatabaseInitializer는 배치 데이터 소스의 초기화 스크립트를 실행하는 역할을 함
    // 데이터베이스 스키마를 자동으로 생성하고 초기화하는 데 사용됨
    @Bean
    @ConditionalOnMissingBean(BatchDataSourceScriptDatabaseInitializer.class)
    BatchDataSourceScriptDatabaseInitializer batchDataSourceInitializer(DataSource dataSource,
                                                                        @BatchDataSource ObjectProvider<DataSource> batchDataSource, BatchProperties properties) {
        return new BatchDataSourceScriptDatabaseInitializer(batchDataSource.getIfAvailable(() -> dataSource),
                properties.getJdbc());
    }

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:mysql://localhost:3306/KMG?serverTimezone=Asia/Seoul&characterEncoding=UTF-8")
                .username("root")
                .password("1234")
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .type(HikariDataSource.class)
                .build();
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}