package com.leverx.javacourse.seller.rating.app.repository;

import com.leverx.javacourse.seller.rating.app.entity.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface UserRepository<T extends User> extends CrudRepository<T, Long> {

    @Query("FROM Seller s JOIN s.sellersItems i WHERE (:gameTitle IS NULL OR i.gameTitle = :gameTitle"
            + "AND (:begin IS NULL OR s.rating >= :begin"
            + "AND (:end IS NULL OR s.rating <= :end ORDER BY s.rating")
    List<T> findAllSellersByCriteria(String gameTitle, BigDecimal begin, BigDecimal end);

    Optional<User> findByLogin(String login);
}
