//package com.project.springbatch.step2;
//
//import org.springframework.batch.core.JobExecution;
//import org.springframework.batch.core.JobExecutionListener;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.stereotype.Component;
//
//@Component
//public class JobRepositoryListener implements JobExecutionListener {
//
//    private final JobRepository jobRepository;
//
//    public JobRepositoryListener(JobRepository jobRepository) {
//        this.jobRepository = jobRepository;
//    }
//
//    @Override
//    public void beforeJob(JobExecution jobExecution) {
//        System.out.println("Before Job");
//    }
//
//    @Override
//    public void afterJob(JobExecution jobExecution) {
//        String jobName = jobExecution.getJobInstance().getJobName();
//
//        JobExecution lastJobExecution = jobRepository.getLastJobExecution(jobName, jobExecution.getJobParameters());
//        System.out.println("jobName = " + jobName);
//    }
//}
