package com.project.springbatch.web.controller;

import com.project.springbatch.web.model.CustomerVO;
import com.project.springbatch.web.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    private final JobLauncher jobLauncher;
    private final JobRegistry jobRegistry;


    @GetMapping("/haha")
    public String runJob() {
        try {
            Job job = jobRegistry.getJob("batchJob");
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();

            jobLauncher.run(job, jobParameters);
            return " job has been initiated.";
        } catch (Exception e) {
            return "Error occurred while starting the job: " + e.getMessage();
        }
    }

    @GetMapping("/test")
    public List<CustomerVO>  selectCustomerAll() {
        return customerService.selectCustomerList();
    }

    @GetMapping("/tset1")
    public String runT1() {
        try {
            Job job = jobRegistry.getJob("apiJob");
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();

            jobLauncher.run(job, jobParameters);
            return " job has been initiated.";
        } catch (Exception e) {
            return "Error occurred while starting the job: " + e.getMessage();
        }
    }
}
