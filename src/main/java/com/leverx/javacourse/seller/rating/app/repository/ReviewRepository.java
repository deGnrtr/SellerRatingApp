package com.leverx.javacourse.seller.rating.app.repository;

import com.leverx.javacourse.seller.rating.app.entity.Review;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends CrudRepository<Review, Long> {

    List<Review> findAllReviewByStatus(String status);

    Optional<Review> findByIdAndStatus(Long id, String status);
}
