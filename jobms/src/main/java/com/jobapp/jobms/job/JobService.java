package com.jobapp.jobms.job;

import com.jobapp.jobms.job.dto.JobWithcompanyDTO;

import java.util.List;

public interface JobService {

    List<JobWithcompanyDTO> findAll();
    void createJob(Job job);

    Job getJobById(Long id);

    void deleteJob(Long id);

    boolean updateJob(Long id, Job updatedJob);
}
