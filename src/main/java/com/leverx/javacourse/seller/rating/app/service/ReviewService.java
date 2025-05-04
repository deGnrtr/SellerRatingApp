package com.leverx.javacourse.seller.rating.app.service;

import com.leverx.javacourse.seller.rating.app.entity.Review;
import com.leverx.javacourse.seller.rating.app.entity.Seller;
import com.leverx.javacourse.seller.rating.app.entity.User;
import com.leverx.javacourse.seller.rating.app.entity.UserRoles;
import com.leverx.javacourse.seller.rating.app.exception.EntityNotFoundException;
import com.leverx.javacourse.seller.rating.app.exception.UnauthorisedDataModification;
import com.leverx.javacourse.seller.rating.app.mapper.ReviewMapper;
import com.leverx.javacourse.seller.rating.app.repository.ReviewRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {
    private final ReviewRepository repository;
    private final UserService userService;
    private final ReviewMapper reviewDtoMapper;

    public ReviewService(ReviewRepository repository, UserService userService, ReviewMapper reviewDtoMapper) {
        this.repository = repository;
        this.userService = userService;
        this.reviewDtoMapper = reviewDtoMapper;
    }

    @Transactional(readOnly = true)
    public Review findByIdAndStatus(Long id, String status) {
        Optional<Review> requestedComment = repository.findByIdAndStatus(id, status);
        return requestedComment.orElseThrow(() -> new EntityNotFoundException("No review found matching request!"));
    }

    @Transactional(readOnly = true)
    public Review findById(Long id) {
        Optional<Review> requestedComment = repository.findById(id);
        return requestedComment.orElseThrow(() -> new EntityNotFoundException("No review found matching request!"));
    }

    @Transactional(readOnly = true)
    public List<Review> findAllReviews(String status) {
        return repository.findAllReviewByStatus(status);
    }

    @Transactional
    public Review save(Review review) {
        return repository.save(review);
    }

    @Transactional
    public void deleteById(Long commentId) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Review review = findById(commentId);
        if (userDetails.getUsername().equals(review.getAuthor().getLogin()) || userDetails.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority)
                .anyMatch(a -> UserRoles.ADMINISTRATOR.getAuthority().equals(a))) {
            repository.deleteById(commentId);
        } else throw new UnauthorisedDataModification("Lack of rights.");
    }

    @Transactional
    public Review setReview(Review review, Long id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User seller = userService.findById(id);
        User author = userService.findByLogin(userDetails.getUsername());
        if (UserRoles.SELLER.equals(seller.getRole()) && !(seller.getId().equals(author.getId()))){
            review.setSeller(seller);
            review.setAuthor(author);
        }else throw new UnauthorisedDataModification("You are not allowed to set review to your own profile!");
        return review;
    }

    @Transactional
    public Review updateReview(Long reviewId, Review newReview) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Review review = findById(reviewId);
        Review updatedReview;
        if (userDetails.getUsername().equals(review.getAuthor().getLogin()) || userDetails.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority)
                .anyMatch(a -> UserRoles.ADMINISTRATOR.getAuthority().equals(a))) {
            userService.updateRating((Seller) review.getSeller(), review.getRatingFromReview(), newReview.getRatingFromReview());
            updatedReview = reviewDtoMapper.updateReview(review, newReview);
        } else throw new UnauthorisedDataModification("Lack of rights.");
        return updatedReview;
    }

    @Transactional
    public Review verifyReview(Long id){
        Review targetReview = findByIdAndStatus(id, "NOT_VERIFIED");
        Seller targetSeller = (Seller) targetReview.getSeller();
        userService.calculateRating(targetSeller, targetReview.getRatingFromReview());
        targetReview.setStatus("VERIFIED");
        return targetReview;
    }

}
