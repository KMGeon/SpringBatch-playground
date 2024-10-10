package com.project.springbatch.step9.ClassifierCompositeItemProcessor;

import org.springframework.batch.item.ItemProcessor;

public class CustomItemProcessor2 implements ItemProcessor<ProcessorInfo, ProcessorInfo> {
    @Override
    public ProcessorInfo process(ProcessorInfo item) throws Exception {
        System.out.println("processor2 = " + item);
        return item;
    }
}
