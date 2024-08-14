package com.jobapp.jobms.job;

import java.util.List;

public interface JobService {

    List<Job> findAll();
    void createJob(Job job);

    Job getJobById(Long id);

    void deleteJob(Long id);

    boolean updateJob(Long id, Job updatedJob);
}
