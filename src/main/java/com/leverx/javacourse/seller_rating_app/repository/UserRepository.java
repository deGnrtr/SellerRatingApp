package com.leverx.javacourse.seller_rating_app.repository;

import com.leverx.javacourse.seller_rating_app.entity.model.Seller;
import com.leverx.javacourse.seller_rating_app.entity.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.math.BigDecimal;
import java.util.List;

public interface UserRepository<T extends User> extends CrudRepository<T, Long> {

    @Query("SELECT s FROM Seller s ORDER BY s.rating")
    List<Seller> findAllSellersByRating();

    @Query("SELECT s FROM Seller s WHERE s.rating BETWEEN :ratingStarts AND :ratingEnds ORDER BY s.rating")
    List<Seller> findSellerByRatingRange(BigDecimal ratingStarts, BigDecimal ratingEnds);

}
