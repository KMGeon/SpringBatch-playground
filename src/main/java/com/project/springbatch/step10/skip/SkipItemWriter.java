package com.project.springbatch.step10.skip;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class SkipItemWriter implements ItemWriter<String> {
    private int cnt = 0;

    @Override
    public void write(Chunk<? extends String> chunk) throws Exception {
        List<? extends String> items = chunk.getItems();
        // 456
        for (String item : items) {
            if (item.equals("-12")) {
                throw new SkippableException("Write failed cnt " + cnt);
            } else {
                System.out.println("ItemWriter" + item);
            }
        }

    }
}
