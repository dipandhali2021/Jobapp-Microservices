package com.jobapp.jobms.job.dto;

import com.jobapp.jobms.job.Job;
import com.jobapp.jobms.job.external.Company;

public class JobWithcompanyDTO {
    private Job job;
    private Company company;

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
