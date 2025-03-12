package com.leverx.javacourse.seller_rating_app.service;

import com.leverx.javacourse.seller_rating_app.entity.model.Comment;
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

    private UserRepository UserRepository;
    private CommentService commentService;
    private ItemService itemService;

    public UserService(UserRepository repository, CommentService commentService, ItemService itemService) {
        this.UserRepository = repository;
        this.commentService = commentService;
        this.itemService = itemService;
    }

    @Transactional
    public User findById(Long id) {
        Optional<User> requestedUser = UserRepository.findById(id);
        return requestedUser.orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public User save(User user) {
        User saved = UserRepository.save(user);
        if (saved.getComments() != null){
            for(Comment comment: saved.getComments()){
                comment.setAuthor(user);
                commentService.save(comment);
            }
        }
        return saved;
    }

    @Transactional
    public void deleteById(Long id) {
        UserRepository.deleteById(id);
    }

    @Transactional
    public List<User> getUsersByRating(BigDecimal rating) {
        return UserRepository.findByRatingOrderByRatingDesc(rating);
    }

    @Transactional
    public List<User> getUsersInRatingRange(BigDecimal begin, BigDecimal ends) {
        return UserRepository.findByRatingRange(begin, ends);
    }
}
