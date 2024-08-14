package com.jobapp.reviewms.review;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews(@RequestParam Long companyId) {
        return ResponseEntity.ok(reviewService.getAllReviews(companyId));
    }

    @PostMapping
    public ResponseEntity<String> createReview(@RequestParam Long companyId, @RequestBody Review review) {
        boolean isReviewSaved = reviewService.createReview(companyId, review);
        if(isReviewSaved) {
            return ResponseEntity.ok("Review created successfully");
        }
        return ResponseEntity.badRequest().body("Company not found");
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<Review> getReview(@PathVariable Long reviewId) {
        Review review = reviewService.getReview(reviewId);
        if(review != null) {
            return ResponseEntity.ok(review);
        }
        return ResponseEntity.notFound().build();
    }


    @PutMapping("/{reviewId}")
    public ResponseEntity<String> updateReview( @PathVariable Long reviewId, @RequestBody Review review) {
        boolean isReviewUpdated = reviewService.updateReview(reviewId, review);
        if(isReviewUpdated) {
            return ResponseEntity.ok("Review updated successfully");
        }
        return ResponseEntity.badRequest().body("Review not found");
    }


    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview( @PathVariable Long reviewId) {
        boolean isReviewDeleted = reviewService.deleteReview( reviewId);
        if(isReviewDeleted) {
            return ResponseEntity.ok("Review deleted successfully");
        }
        return ResponseEntity.badRequest().body("Review not found");
    }
}
