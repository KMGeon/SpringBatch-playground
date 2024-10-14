package com.project.springbatch.step11;

import com.project.springbatch.web.model.CustomerVO;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class CustomItemReadListener implements ItemReadListener<CustomerVO> {
    @Override
    public void beforeRead() {

    }

    @Override
    public void afterRead(CustomerVO item) {
        // 스레드 이름, item 이름
        System.out.println("Reader Thread : " + Thread.currentThread().getName() +" >>  read item : " + item.getId());
    }

    @Override
    public void onReadError(Exception ex) {

    }
}