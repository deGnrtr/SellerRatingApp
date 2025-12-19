package com.leverx.javacourse.seller.rating.app.service;

import com.leverx.javacourse.seller.rating.app.entity.Review;
import com.leverx.javacourse.seller.rating.app.entity.Seller;
import com.leverx.javacourse.seller.rating.app.entity.User;
import com.leverx.javacourse.seller.rating.app.entity.UserRoles;
import com.leverx.javacourse.seller.rating.app.entity.Visitor;
import com.leverx.javacourse.seller.rating.app.exception.EntityNotFoundException;
import com.leverx.javacourse.seller.rating.app.repository.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Captor
    ArgumentCaptor<User> capturedSeller = ArgumentCaptor.forClass(User.class);
    @Captor
    ArgumentCaptor<BigDecimal> capturedNewRating = ArgumentCaptor.forClass(BigDecimal.class);
    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private UserService userService;
    @InjectMocks
    private ReviewService reviewService;
    private Review verifiedReview;
    private Review nonVerifiedReview;

    @BeforeEach
    void setUp() {
        Seller seller = new Seller(1L, "Test seller", "Password", "Biba", "Boba", "test@mail.com", LocalDate.now(),
                UserRoles.SELLER, null, "VERIFIED", null, null, BigDecimal.TWO);
        Visitor visitor = new Visitor(1L, "Test visitor", "Password", "Pupa", "Lupa", "test@mail.com", LocalDate.now(),
                UserRoles.VISITOR, null, "VERIFIED");
        verifiedReview = new Review(1L, "First review", LocalDate.now(), "VERIFIED", BigDecimal.TEN, visitor, seller);
        nonVerifiedReview = new Review(2L, "Second review", LocalDate.now(), "NOT_VERIFIED", BigDecimal.TWO, visitor, seller);
    }

    @Test
    void findByIdAndStatus_Successfully() {
        when(reviewRepository.findByIdAndStatus(1L, "VERIFIED")).thenReturn(Optional.of(verifiedReview));

        var requestedReview = reviewService.findByIdAndStatus(1L, "VERIFIED");

        assertEquals(verifiedReview, requestedReview);
        verify(reviewRepository, times(1)).findByIdAndStatus(1L, "VERIFIED");
    }

    @Test
    void findByIdAndStatus_FinishedWithException() {
        when(reviewRepository.findByIdAndStatus(anyLong(), anyString())).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> reviewService.findByIdAndStatus(anyLong(), anyString()));

        assertEquals("No review found matching request!", exception.getMessage());
    }

    @Test
    void findById_Successfully() {
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(verifiedReview));

        var requestedReview = reviewService.findById(1L);

        assertEquals(verifiedReview, requestedReview);
        verify(reviewRepository, times(1)).findById(1L);
    }

    @Test
    void findById_FinishedWithException() {
        when(reviewRepository.findById(anyLong())).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> reviewService.findById(anyLong()));

        assertEquals("No review found matching request!", exception.getMessage());
    }

    @Test
    void findAllReviews_Successfully() {
        when(reviewRepository.findAllReviewByStatus("VERIFIED")).thenReturn(List.of(verifiedReview));

        List<Review> reviews = reviewService.findAllReviews("VERIFIED");

        assertEquals(verifiedReview, reviews.getFirst());
        verify(reviewRepository, times(1)).findAllReviewByStatus("VERIFIED");
    }

    @Test
    void save_Successfully() {
        when(reviewRepository.save(verifiedReview)).thenReturn(verifiedReview);

        var savedReview = reviewService.save(verifiedReview);

        assertEquals(verifiedReview, savedReview);
        verify(reviewRepository, times(1)).save(verifiedReview);
    }

    @Test
    void verifyReview_Successfully() {
        when(reviewRepository.findByIdAndStatus(2L, "NOT_VERIFIED")).thenReturn(Optional.of(nonVerifiedReview));
        doNothing().when(userService).calculateRating((Seller) capturedSeller.capture(), capturedNewRating.capture());

        var verifiedReview = reviewService.verifyReview(2L);

        assertEquals(nonVerifiedReview.getSeller(), capturedSeller.getValue());
        assertEquals(nonVerifiedReview.getRatingFromReview(), capturedNewRating.getValue());
        assertEquals("VERIFIED", verifiedReview.getStatus());
        verify(userService, atLeastOnce()).calculateRating((Seller) nonVerifiedReview.getSeller(), nonVerifiedReview.getRatingFromReview());
    }
}