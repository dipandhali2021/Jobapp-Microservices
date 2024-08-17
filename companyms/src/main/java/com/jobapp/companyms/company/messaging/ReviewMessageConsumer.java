package com.jobapp.companyms.company.messaging;

import com.jobapp.companyms.company.CompanyService;
import com.jobapp.companyms.company.dto.ReviewMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class ReviewMessageConsumer {
    private final CompanyService companyService;

    public ReviewMessageConsumer(CompanyService companyService) {
        this.companyService = companyService;
    }

    @RabbitListener(queues = "companyRatingQueue")
    public void consumeMessage(ReviewMessage reviewMessage) {
        try {
            companyService.updateCompanyRating(reviewMessage);
        } catch (Exception e) {
            System.out.println("Error processing review message: " + e.getMessage());
        }
    }
}