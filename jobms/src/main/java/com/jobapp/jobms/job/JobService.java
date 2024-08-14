package com.jobapp.jobms.job;

import com.jobapp.jobms.job.dto.jobDTO;

import java.util.List;

public interface JobService {

    List<jobDTO> findAll();
    void createJob(Job job);

    jobDTO getJobById(Long id);

    void deleteJob(Long id);

    boolean updateJob(Long id, Job updatedJob);
}
