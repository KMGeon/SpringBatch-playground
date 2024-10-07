package com.project.springbatch.step9.CompositeItemProcessor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class CustomItemProcessor implements ItemProcessor<String, String> {

    int cnt = 0;

    @Override
    public String process(String item) throws Exception {
        cnt++;
        return (item + cnt +"[Processor1]").toUpperCase();
    }
}
