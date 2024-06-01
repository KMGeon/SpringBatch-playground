//package com.project.springbatch;
//
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.JobParametersBuilder;
//import org.springframework.batch.core.launch.JobLauncher;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.stereotype.Component;
//
//import java.util.UUID;
//
//@Component
//public class JobRunner implements ApplicationRunner {
//
//    private final JobLauncher jobLauncher;
//
//    private final Job job;
//
//    public JobRunner(
//            JobLauncher jobLauncher,
//            Job job
//    ) {
//        this.jobLauncher = jobLauncher;
//        this.job = job;
//    }
//
//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//        jobLauncher.run(job, new JobParametersBuilder()
//                .addString("UUID", UUID.randomUUID().toString())
//                .toJobParameters());
//    }
//}
