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
    public final ReviewMapper reviewMapper;

    public ReviewController(ReviewService reviewService, UserService userService, ReviewMapper reviewMapper) {
        this.reviewService = reviewService;
        this.reviewMapper = reviewMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewResponseDto> getReview(@PathVariable Long id) {
        Review review = reviewService.findByIdAndStatus(id, "VERIFIED");
        return ResponseEntity.status(HttpStatus.OK).body(reviewMapper.toReviewResponseDto(review));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> deleteReview(@PathVariable Long id) {
        reviewService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    //FIXME fix rating update
    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ReviewResponseDto> updateReview(@PathVariable Long id, @RequestBody ReviewCreateDto reviewCreateDto){
        Review newReview = reviewMapper.toReview(reviewCreateDto);
        Review updatedReview = reviewService.updateReview(id, newReview);
        return ResponseEntity.status(HttpStatus.OK).body(reviewMapper.toReviewResponseDto(reviewService.save(updatedReview)));
    }
}
