//package com.project.springbatch.step7;
//
//import org.springframework.batch.item.file.LineMapper;
//import org.springframework.batch.item.file.mapping.FieldSetMapper;
//import org.springframework.batch.item.file.transform.LineTokenizer;
//
///**
// *  LineMapper
// */
//public class DefaultLineMapper<T> implements LineMapper<T> {
//
//    private LineTokenizer lineTokenizer;
//    private FieldSetMapper<T> fieldSetMapper;
//
//    @Override
//    public T mapLine(String line, int lineNumber) throws Exception {
//        return fieldSetMapper.mapFieldSet(lineTokenizer.tokenize(line));
//    }
//
//    public void setLineTokenizer(LineTokenizer lineTokenizer){
//        this.lineTokenizer = lineTokenizer;
//    }
//
//    public void setFieldSetMapper(FieldSetMapper<T> tFieldSetMapper){
//        this.fieldSetMapper = tFieldSetMapper;
//    }
//}
