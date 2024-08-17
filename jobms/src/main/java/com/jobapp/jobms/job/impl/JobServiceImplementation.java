package com.jobapp.jobms.job.impl;

import com.jobapp.jobms.job.Job;
import com.jobapp.jobms.job.JobRepository;
import com.jobapp.jobms.job.JobService;
import com.jobapp.jobms.job.clients.CompanyClient;
import com.jobapp.jobms.job.clients.ReviewClient;
import com.jobapp.jobms.job.dto.jobDTO;
import com.jobapp.jobms.job.external.Company;
import com.jobapp.jobms.job.external.Review;
import com.jobapp.jobms.job.mapper.JobMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.http.HttpMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobServiceImplementation implements JobService {
    //    private final List<Job> jobs = new ArrayList<>();
    JobRepository jobRepository;

    @Autowired
    RestTemplate restTemplate;

    private CompanyClient companyClient;
    private ReviewClient reviewClient;
    int attempts = 0;

    public JobServiceImplementation(JobRepository jobRepository, CompanyClient companyClient, ReviewClient reviewClient) {
        this.jobRepository = jobRepository;
        this.companyClient = companyClient;
        this.reviewClient = reviewClient;
    }


    @Override
//    @CircuitBreaker(name = "jobBreaker", fallbackMethod = "companyBreakerFallback")
//    @Retry(name = "jobBreaker", fallbackMethod = "companyBreakerFallback")
    @RateLimiter(name = "jobBreaker")
    public List<jobDTO> findAll() {
        System.out.println("Attempts: " + attempts++);
        List<Job> jobs = jobRepository.findAll();
        return jobs.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public List<jobDTO> companyBreakerFallback(Exception e) {
        System.out.println("Company service is down, returning empty list");
        return List.of();
    }

    private jobDTO convertToDto(Job job) {
//        RestTemplate restTemplate = new RestTemplate();
        Company company = companyClient.getCompany(job.getCompanyId());
        List<Review> reviews = reviewClient.getReviews(company.getId());

        jobDTO jobDTO = JobMapper.mapToJobWithCompanyDTO(job, company, reviews);

        return jobDTO;
    }

    @Override
    public void createJob(Job job) {
        jobRepository.save(job);
    }

    @Override
    public jobDTO getJobById(Long id) {

        Job job = jobRepository.findById(id).orElse(null);
        if (job != null) {
            return convertToDto(job);
        }
        return null;
    }

    @Override
    public void deleteJob(Long id) {
        try {
            jobRepository.deleteById(id);
        } catch (Exception e) {
            System.out.println("Error deleting job with id: " + id);
        }
    }

    @Override
    public boolean updateJob(Long id, Job updatedJob) {
        Optional<Job> jobOptional = jobRepository.findById(id);
        if (jobOptional.isPresent()) {
            Job job = jobOptional.get();
            job.setTitle(updatedJob.getTitle());
            job.setDescription(updatedJob.getDescription());
            job.setMinSalary(updatedJob.getMinSalary());
            job.setMaxSalary(updatedJob.getMaxSalary());
            job.setLocation(updatedJob.getLocation());
            jobRepository.save(job);
            return true;
        }
        return false;
    }
}
