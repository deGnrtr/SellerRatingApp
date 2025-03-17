package com.leverx.javacourse.seller.rating.app.service;

import com.leverx.javacourse.seller.rating.app.entity.model.Comment;
import com.leverx.javacourse.seller.rating.app.entity.model.UserRoles;
import com.leverx.javacourse.seller.rating.app.exception.EntityNotFoundException;
import com.leverx.javacourse.seller.rating.app.exception.UnauthorisedDataModification;
import com.leverx.javacourse.seller.rating.app.mapper.CommentMapper;
import com.leverx.javacourse.seller.rating.app.repository.CommentRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    private final CommentRepository repository;
    private final UserService userService;
    private final CommentMapper commentDtoMapper;
    private final UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public CommentService(CommentRepository repository, UserService userService, CommentMapper commentDtoMapper) {
        this.repository = repository;
        this.userService = userService;
        this.commentDtoMapper = commentDtoMapper;
    }

    @Transactional(readOnly = true)
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
        Comment comment = findById(commentId);
        if (userDetails.getUsername().equals(comment.getAuthor().getLogin()) || userDetails.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority)
                .anyMatch(a -> UserRoles.ADMINISTRATOR.getAuthority().equals(a))) {
            repository.deleteById(commentId);
        }else throw new UnauthorisedDataModification();
    }

    @Transactional
    public Comment setComment(Comment comment, Long id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        comment.setSeller(userService.findById(id));
        comment.setAuthor(userService.findByLogin(userDetails.getUsername()).orElseThrow(EntityNotFoundException::new));
        return comment;
    }

    @Transactional
    public Comment updateComment(Long commentId, Comment newComment) {
        Comment comment = findById(commentId);
        Comment updatedComment = null;
        if (userDetails.getUsername().equals(comment.getAuthor().getLogin()) || userDetails.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority)
                .anyMatch(a -> UserRoles.ADMINISTRATOR.getAuthority().equals(a))) {
            updatedComment = commentDtoMapper.updateComment(comment, newComment);
        } else throw new UnauthorisedDataModification();
        return updatedComment;
    }
}
