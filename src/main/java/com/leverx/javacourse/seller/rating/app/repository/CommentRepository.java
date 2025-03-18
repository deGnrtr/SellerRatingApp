package com.leverx.javacourse.seller.rating.app.repository;

import com.leverx.javacourse.seller.rating.app.entity.Comment;
import com.leverx.javacourse.seller.rating.app.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends CrudRepository<Comment, Long> {

    List<Comment> findAllCommentByStatus(String status);

    Optional<Comment> findByIdAndStatus(Long id, String status);
}
