package com.leverx.javacourse.seller.rating.app.repository;

import com.leverx.javacourse.seller.rating.app.entity.Seller;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface SellerRepository extends UserRepository<Seller> {

    @Query("FROM Seller s JOIN s.sellersItems i WHERE (:gameTitle IS NULL OR i.gameTitle = :gameTitle) "
            + "AND (:begin IS NULL OR s.rating >= :begin) "
            + "AND (:end IS NULL OR s.rating <= :end) "
            + "AND (s.status = :status) "
            + "ORDER BY s.rating")
    List<Seller> findAllSellersByCriteria(String gameTitle, BigDecimal begin, BigDecimal end, String status);
}
