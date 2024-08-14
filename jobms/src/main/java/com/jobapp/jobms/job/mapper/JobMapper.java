package com.jobapp.jobms.job.mapper;

import com.jobapp.jobms.job.Job;
import com.jobapp.jobms.job.dto.jobDTO;
import com.jobapp.jobms.job.external.Company;
import com.jobapp.jobms.job.external.Review;

import java.util.List;

public class JobMapper {
    public  static jobDTO mapToJobWithCompanyDTO(Job job, Company company,  List<Review> review){
        jobDTO jobDTO = new jobDTO();
        jobDTO.setId(job.getId());
        jobDTO.setTitle(job.getTitle());
        jobDTO.setDescription(job.getDescription());
        jobDTO.setMinSalary(job.getMinSalary());
        jobDTO.setMaxSalary(job.getMaxSalary());
        jobDTO.setLocation(job.getLocation());
        jobDTO.setCompany(company);
        jobDTO.setReview(review);
        return jobDTO;
    }
}
