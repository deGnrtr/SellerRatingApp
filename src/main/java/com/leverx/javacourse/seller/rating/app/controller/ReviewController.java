package com.leverx.javacourse.seller.rating.app.controller;

import com.leverx.javacourse.seller.rating.app.dto.ReviewCreateDto;
import com.leverx.javacourse.seller.rating.app.dto.ReviewResponseDto;
import com.leverx.javacourse.seller.rating.app.entity.Review;
import com.leverx.javacourse.seller.rating.app.mapper.ReviewMapper;
import com.leverx.javacourse.seller.rating.app.service.ReviewService;
import com.leverx.javacourse.seller.rating.app.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;
    public ReviewMapper reviewMapper;

    public ReviewController(ReviewService reviewService, UserService userService, ReviewMapper reviewMapper) {
        this.reviewService = reviewService;
        this.reviewMapper = reviewMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewResponseDto> getComment(@PathVariable Long id) {
        Review review = reviewService.findByIdAndStatus(id, "VERIFIED");
        return ResponseEntity.status(HttpStatus.OK).body(reviewMapper.toReviewResponseDto(review));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId) {
        reviewService.deleteById(commentId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ReviewResponseDto> updateComment(@PathVariable Long commentId, @RequestBody ReviewCreateDto reviewCreateDto){
        Review newReview = reviewMapper.toReview(reviewCreateDto);
        Review updatedReview = reviewService.updateReview(commentId, newReview);
        return ResponseEntity.status(HttpStatus.OK).body(reviewMapper.toReviewResponseDto(reviewService.save(updatedReview)));
    }
}
