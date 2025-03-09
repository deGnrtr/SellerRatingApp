package com.leverx.javacourse.seller_rating_app.service;

import com.leverx.javacourse.seller_rating_app.entity.Comment;
import com.leverx.javacourse.seller_rating_app.repository.CommentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class CommentService {
    private final CommentRepository repository;

    public CommentService(CommentRepository repository) {
        this.repository = repository;
    }

    public Comment findById(int id) {
        Optional<Comment> requestedComment = repository.findById(id);
        return requestedComment.orElseThrow(RuntimeException::new);
    }

    public Comment save(Comment item) {
        return repository.save(item);
    }

    public void deleteById(int id) {
        repository.deleteById(id);
    }
}
