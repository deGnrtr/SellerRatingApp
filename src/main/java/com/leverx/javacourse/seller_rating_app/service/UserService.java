package com.leverx.javacourse.seller_rating_app.service;

import com.leverx.javacourse.seller_rating_app.entity.model.User;
import com.leverx.javacourse.seller_rating_app.exception.EntityNotFoundException;
import com.leverx.javacourse.seller_rating_app.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public User findById(int id) {
        Optional<User> requestedUser = repository.findById(id);
        return requestedUser.orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public User save(User user) {
        return repository.save(user);
    }

    @Transactional
    public void deleteById(int id) {
        repository.deleteById(id);
    }

    @Transactional
    public List<User> getUsersByRating(BigDecimal rating) {
        return repository.findByRatingOrderByRatingDesc(rating);
    }

    @Transactional
    public List<User> getUsersInRatingRange(BigDecimal begin, BigDecimal ends) {
        return repository.findByRatingRange(begin, ends);
    }
}
