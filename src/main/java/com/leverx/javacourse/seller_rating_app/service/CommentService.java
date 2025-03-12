package com.leverx.javacourse.seller_rating_app.service;

import com.leverx.javacourse.seller_rating_app.entity.model.Comment;
import com.leverx.javacourse.seller_rating_app.repository.CommentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentService {
    private final CommentRepository repository;

    public CommentService(CommentRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Comment findById(Long id) {
        Optional<Comment> requestedComment = repository.findById(id);
        return requestedComment.orElseThrow(RuntimeException::new);
    }

    @Transactional
    public Comment save(Comment item) {
        return repository.save(item);
    }

    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
