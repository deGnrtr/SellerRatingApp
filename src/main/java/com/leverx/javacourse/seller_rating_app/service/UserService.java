package com.leverx.javacourse.seller_rating_app.service;

import com.leverx.javacourse.seller_rating_app.entity.model.User;
import com.leverx.javacourse.seller_rating_app.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    private UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User findById(int id) {
        Optional<User> requestedUser = repository.findById(id);
        return requestedUser.orElseThrow(RuntimeException::new);
    }

    public User save(User user) {
        return repository.save(user);
    }

    public void deleteById(int id) {
        repository.deleteById(id);
    }

    public List<User> getUsersByRating(BigDecimal rating) {
        return repository.findByOrderByRatingDesc(rating);
    }

    public List<User> getUsersInRatingRange(BigDecimal begin, BigDecimal ends) {
        return repository.findByRatingRange(begin, ends);
    }
}
