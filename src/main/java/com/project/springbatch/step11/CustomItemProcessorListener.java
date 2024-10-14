package com.project.springbatch.step11;

import com.project.springbatch.web.model.CustomerVO;
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.stereotype.Component;

@Component
public class CustomItemProcessorListener implements ItemProcessListener<CustomerVO, CustomerVO> {
    @Override
    public void beforeProcess(CustomerVO item) {
        ItemProcessListener.super.beforeProcess(item);
    }

    @Override
    public void afterProcess(CustomerVO item, CustomerVO result) {
        System.out.println("Thread : " + Thread.currentThread().getName() +"read item : " + item.getId());
    }

    @Override
    public void onProcessError(CustomerVO item, Exception e) {
        ItemProcessListener.super.onProcessError(item, e);
    }
}