package com.leverx.javacourse.seller_rating_app.service;

import com.leverx.javacourse.seller_rating_app.entity.model.Comment;
import com.leverx.javacourse.seller_rating_app.entity.model.Seller;
import com.leverx.javacourse.seller_rating_app.exception.EntityNotFoundException;
import com.leverx.javacourse.seller_rating_app.repository.CommentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    private final CommentRepository repository;
    private final UserService userService;

    public CommentService(CommentRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    @Transactional
    public Comment findById(Long id) {
        Optional<Comment> requestedComment = repository.findById(id);
        return requestedComment.orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public Comment save(Comment comment){
        return repository.save(comment);
    }

    @Transactional
    public void deleteById(Long commentId) {
        repository.deleteById(commentId);
    }

    @Transactional
    public List<Comment> findAllComments(){
        return (List<Comment>) repository.findAll();
    }
}
