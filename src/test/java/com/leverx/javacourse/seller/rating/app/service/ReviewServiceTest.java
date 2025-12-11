package com.leverx.javacourse.seller.rating.app.service;

import com.leverx.javacourse.seller.rating.app.entity.Review;
import com.leverx.javacourse.seller.rating.app.entity.Seller;
import com.leverx.javacourse.seller.rating.app.entity.UserRoles;
import com.leverx.javacourse.seller.rating.app.entity.Visitor;
import com.leverx.javacourse.seller.rating.app.exception.EntityNotFoundException;
import com.leverx.javacourse.seller.rating.app.repository.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private ReviewService reviewService;

    private Review review;

    @BeforeEach
    void setUp() {
        Seller seller = new Seller(1L, "Test seller", "Password", "Biba", "Boba", "test@mail.com", LocalDate.now(),
                UserRoles.SELLER, null, "VERIFIED", null, null, BigDecimal.TWO);
        Visitor visitor = new Visitor(1L, "Test visitor", "Password", "Pupa", "Lupa", "test@mail.com", LocalDate.now(),
                UserRoles.VISITOR, null, "VERIFIED");
        review = new Review(1L, "First review", LocalDate.now(), "VERIFIED", BigDecimal.TEN, visitor, seller);

    }

    @Test
    void findByIdAndStatus_Successfully() {
        when(reviewRepository.findByIdAndStatus(1L, "VERIFIED")).thenReturn(Optional.of(review));

        Review requestedReview = reviewService.findByIdAndStatus(1L, "VERIFIED");

        assertEquals(review, requestedReview);
        verify(reviewRepository, times(1)).findByIdAndStatus(1L, "VERIFIED");
    }

    @Test
    void findByIdAndStatus_FinishedWithException() {
        when(reviewRepository.findByIdAndStatus(anyLong(), anyString())).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> reviewService.findByIdAndStatus(anyLong(), anyString()));

        assertEquals(exception.getMessage(), "No review found matching request!");
    }

    @Test
    void findById() {
    }

    @Test
    void findAllReviews() {
    }

    @Test
    void save() {
    }

    @Test
    void verifyReview() {
    }
}