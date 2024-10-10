package com.project.springbatch.step9.ClassifierCompositeItemProcessor;

import org.springframework.batch.item.ItemProcessor;

public class CustomItemProcessor1 implements ItemProcessor<ProcessorInfo, ProcessorInfo> {
    @Override
    public ProcessorInfo process(ProcessorInfo item) throws Exception {
        System.out.println("process1 = " + item);
        return item;
    }
}
