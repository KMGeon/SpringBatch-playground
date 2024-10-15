//package com.project.springbatch.step11;//package com.project.springbatch.step12;
//
//import com.project.springbatch.step11.partitioner.ColumnRangePartitioner;
//import com.project.springbatch.web.model.CustomerVO;
//import lombok.RequiredArgsConstructor;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.StepScope;
//import org.springframework.batch.core.job.builder.JobBuilder;
//import org.springframework.batch.core.launch.support.RunIdIncrementer;
//import org.springframework.batch.core.partition.support.Partitioner;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.core.step.builder.StepBuilder;
//import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
//import org.springframework.batch.item.database.JdbcBatchItemWriter;
//import org.springframework.batch.item.database.JdbcPagingItemReader;
//import org.springframework.batch.item.database.Order;
//import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.task.SimpleAsyncTaskExecutor;
//import org.springframework.core.task.TaskExecutor;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import javax.sql.DataSource;
//import java.util.HashMap;
//import java.util.Map;
//
//@Configuration
//@RequiredArgsConstructor
//public class PartitioningConfiguration {
//    private final JobRepository jobRepository;
//    private final PlatformTransactionManager transactionManager;
//    private final DataSource dataSource;
//
//    private static final String JOB_NAME = "partitioningJob";
//
//    @Autowired
//    @Qualifier("sqlSessionTemplate")
//    private SqlSessionTemplate sqlSessionTemplate;
//
//    private static final int CHUNK_SIZE = 10;
//
//    @Bean
//    public Job job(JobRepository jobRepository, PlatformTransactionManager transactionManager) throws Exception {
//        return new JobBuilder("batchJob", jobRepository)
//                .incrementer(new RunIdIncrementer())
//                .start(masterStep())
//                .listener(new StepWatchJobListener())
//                .build();
//    }
//
//    @Bean
//    public Step masterStep() {
//        return new StepBuilder("masterStep", jobRepository)
//                .partitioner(slaveStep().getName(), partitioner())
//                .step(slaveStep())
//                .gridSize(4)
//                .taskExecutor(executor())
//                .build();
//    }
//
//
//    @Bean(name = JOB_NAME+"_taskPool")
//    public TaskExecutor executor() {
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        executor.setCorePoolSize(4);
//        executor.setMaxPoolSize(8);
//        executor.setThreadNamePrefix("partition-thread");
//        executor.setWaitForTasksToCompleteOnShutdown(Boolean.TRUE);
//        executor.initialize();
//        return executor;
//    }
//
//    /**
//     * slaveStep 은 chunk기반
//     */
//    @Bean
//    public Step slaveStep() {
//        return new StepBuilder("slaveStep", jobRepository)
//                .<CustomerVO, CustomerVO>chunk(CHUNK_SIZE, transactionManager)
//                .reader(pagingItemReader(null, null))
//                .writer(customerItemWriter())
//                .build();
//    }
//
//    @Bean
//    public Partitioner partitioner(){
//        ColumnRangePartitioner columnRangePartitioner = new ColumnRangePartitioner();
//
//        columnRangePartitioner.setColumn("id");
//        columnRangePartitioner.setDataSource(this.dataSource);
//        columnRangePartitioner.setTable("customer");
//        return columnRangePartitioner;
//    }
//
//    /**
//     * jobScope : Job에 설정한다.
//     * StepScope : ItemReader, Writer또는 Step에 설정한다.
//     *
//     * reading 5001 to 7500thread name : partition-thread2
//     * reading 2501 to 5000thread name : partition-thread1
//     * reading 7501 to 10000thread name : partition-thread3
//     * reading 1 to 2500thread name : partition-thread4
//     */
//    @Bean
//    @StepScope
//    public JdbcPagingItemReader<CustomerVO> pagingItemReader(
//            @Value("#{stepExecutionContext['minValue']}") Long minValue,
//            @Value("#{stepExecutionContext['maxValue']}")Long maxValue) {
//        String name = Thread.currentThread().getName();
//        System.out.println("reading " + minValue + " to " + maxValue + "thread name : " + name);
//        JdbcPagingItemReader<CustomerVO> reader = new JdbcPagingItemReader<>();
//
//        reader.setDataSource(this.dataSource);
//        reader.setFetchSize(1000);
//        reader.setRowMapper(new CustomerRowMapper());
//
//        MySqlPagingQueryProvider queryProvider = new MySqlPagingQueryProvider();
//        queryProvider.setSelectClause("id, first_name, last_name, birthdate");
//        queryProvider.setFromClause("from customer");
//        queryProvider.setWhereClause("where id >= " + minValue + " and id < " + maxValue);
//
//        Map<String, Order> sortKeys = new HashMap<>(1);
//
//        sortKeys.put("id", Order.ASCENDING);
//
//        queryProvider.setSortKeys(sortKeys);
//
//        reader.setQueryProvider(queryProvider);
//
//        return reader;
//    }
//
//
//    @Bean
//    @StepScope
//    public JdbcBatchItemWriter<CustomerVO> customerItemWriter() {
//        JdbcBatchItemWriter<CustomerVO> itemWriter = new JdbcBatchItemWriter<>();
//
//        itemWriter.setDataSource(this.dataSource);
//        itemWriter.setSql("insert into customer2 values (:id, :first_name, :last_name, :birthdate)");
//        itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider());
//        itemWriter.afterPropertiesSet();
//
//        return itemWriter;
//    }
//}