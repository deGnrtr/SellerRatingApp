package com.leverx.javacourse.seller_rating_app.repository;

import com.leverx.javacourse.seller_rating_app.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {

    List<User> findByOrderByRatingDesc(BigDecimal rating);

    @Query("SELECT u FROM User u WHERE u.rating BETWEEN :ratingStarts AND :ratingEnds ORDER BY u.rating")
    List<User> findByRatingRange(@Param("ratingStarts") BigDecimal start, @Param("ratingEnds") BigDecimal end);
}
