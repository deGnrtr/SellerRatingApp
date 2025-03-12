package com.leverx.javacourse.seller_rating_app.repository;

import com.leverx.javacourse.seller_rating_app.entity.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.math.BigDecimal;
import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.userRole = 'SELLER' ORDER BY u.rating")
    List<User> findByRatingOrderByRatingDesc();

    @Query("SELECT u FROM User u WHERE u.userRole = 'SELLER' AND (u.rating BETWEEN :ratingStarts AND :ratingEnds) ORDER BY u.rating")
    List<User> findByRatingRange(BigDecimal ratingStarts, BigDecimal ratingEnds);
}
