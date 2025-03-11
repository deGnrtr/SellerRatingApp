package com.leverx.javacourse.seller_rating_app.repository;

import com.leverx.javacourse.seller_rating_app.entity.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.math.BigDecimal;
import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {

    List<User> findByRatingOrderByRatingDesc(BigDecimal rating);

    @Query("SELECT u FROM User u WHERE u.rating BETWEEN :ratingStarts AND :ratingEnds ORDER BY u.rating")
    List<User> findByRatingRange(BigDecimal ratingStarts, BigDecimal ratingEnds);
}
