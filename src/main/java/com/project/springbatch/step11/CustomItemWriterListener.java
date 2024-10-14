package com.project.springbatch.step11;

import com.project.springbatch.web.model.CustomerVO;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.item.Chunk;
import org.springframework.stereotype.Component;

@Component
public class CustomItemWriterListener implements ItemWriteListener<CustomerVO> {
    @Override
    public void beforeWrite(Chunk<? extends CustomerVO> items) {
        ItemWriteListener.super.beforeWrite(items);
    }

    @Override
    public void afterWrite(Chunk<? extends CustomerVO> items) {
        System.out.println("Write Thread : " + Thread.currentThread().getName() +" >> read item : " + items.size());
    }

    @Override
    public void onWriteError(Exception exception, Chunk<? extends CustomerVO> items) {
        ItemWriteListener.super.onWriteError(exception, items);
    }
}