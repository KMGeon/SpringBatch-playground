//package com.project.springbatch.step2;
//
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.JobParameters;
//import org.springframework.batch.core.JobParametersBuilder;
//import org.springframework.batch.core.launch.JobLauncher;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.stereotype.Component;
//
//@Component
//public class JobParameterTest implements ApplicationRunner{
//
//    @Autowired
//    private JobLauncher jobLauncher;
//
//    @Autowired
//    private Job job;
//
//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//        JobParameters params = new JobParametersBuilder()
////                .addString("UUID", UUID.randomUUID().toString())
//                .addString("string", "name1")
////                .addLocalDate("localdate", LocalDate.now())
////                .addDate("date", new Date())
////                .addLocalDateTime("LocalDateTime", LocalDateTime.now())
//                .addDouble("double", 1.5)
//                .toJobParameters();
//
//        jobLauncher.run(job, params);
//    }
//}
