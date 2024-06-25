package com.project.springbatch.step6;

import com.project.springbatch.BatchConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.ArrayList;
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

    /**
     *  처음에 0~6에서 chunk size가 2 이기 때문에
     *  {0,1} , {2,3} , {4,5} ,{6}
     *
     *  // 01, write update, 23, exception이 발생한다.
     *   청크 단위로 처리하기 때문에 index가 6인 5가 exception을 처리한다.
     *
     */
    @Bean
    public Job job(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobBuilder("batchJob", jobRepository)
                .start(step1())
                .build();
    }

    @Bean
    public Step step1() {
        return new StepBuilder("step1", jobRepository)
                .<String, String>chunk(CHUNK_SIZE, transactionManager) // SimpleStepBuilder > TaskletStep > ChunkOrientedTasklet 생성
                .reader(itemReader())
                .writer(itemWriter())
                .build();
    }

    private ItemWriter<? super String> itemWriter() {

        return new CustomItemWriter();

    }


    private CustomItemStreamReader itemReader() {
        List<String> items = new ArrayList<>(10);

        for (int i = 0; i < 10; i++) {
            items.add(String.valueOf(i));
        }
        return new CustomItemStreamReader(items);
    }


    class CustomItemStreamReader implements ItemStreamReader<String> {

        private final List<String> items;
        private int index = -1;
        private boolean restart = false;

        public CustomItemStreamReader(List<String> items) {
            this.items = items;
            this.index = 0;
        }

        @Override
        public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
            String item = null;

            if (this.index < this.items.size()) {
                item = this.items.get(index);
                index++;
            }

            if (this.index == 6 && !restart) throw new RuntimeException("Restart is required");
            return item;
        }


        @Override
        public void open(ExecutionContext executionContext) throws ItemStreamException {
            if (executionContext.containsKey("index")) {
                index = executionContext.getInt("index");
                this.restart = true;
            } else {
                System.out.println("초기화");
                index = 0;
                executionContext.put("index", index);
            }
        }

        @Override
        public void update(ExecutionContext executionContext) throws ItemStreamException {
            executionContext.put("index", index);
        }

        /**
         * 예외가 발생하면 실행
         */
        @Override
        public void close() throws ItemStreamException {
            System.out.println(" ==== close ==== ");
        }
    }


    class CustomItemWriter implements ItemStreamWriter<String> {

        @Override
        public void write(Chunk<? extends String> chunk) throws Exception {
            chunk.getItems().forEach(System.out::println);

        }

        @Override
        public void open(ExecutionContext executionContext) throws ItemStreamException {
            System.out.println("writer open");
        }

        @Override
        public void update(ExecutionContext executionContext) throws ItemStreamException {
            System.out.println("writer update");
        }

        @Override
        public void close() throws ItemStreamException {
            System.out.println("writer close");
        }
    }
}
