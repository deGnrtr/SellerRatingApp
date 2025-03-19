package com.leverx.javacourse.seller.rating.app.repository;

import com.leverx.javacourse.seller.rating.app.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository<T extends User> extends CrudRepository<T, Long> {

    Optional<T> findByLogin(String login);

    Optional<T> findByIdAndStatus(Long id, String status);
}
