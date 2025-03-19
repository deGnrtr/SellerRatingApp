package com.leverx.javacourse.seller.rating.app.service;

import com.leverx.javacourse.seller.rating.app.entity.Comment;
import com.leverx.javacourse.seller.rating.app.entity.Seller;
import com.leverx.javacourse.seller.rating.app.entity.User;
import com.leverx.javacourse.seller.rating.app.entity.UserRoles;
import com.leverx.javacourse.seller.rating.app.entity.Visitor;
import com.leverx.javacourse.seller.rating.app.exception.EntityNotFoundException;
import com.leverx.javacourse.seller.rating.app.exception.UnauthorisedDataModification;
import com.leverx.javacourse.seller.rating.app.mapper.CommentMapper;
import com.leverx.javacourse.seller.rating.app.repository.CommentRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    private final CommentRepository repository;
    private final UserService userService;
    private final CommentMapper commentDtoMapper;

    public CommentService(CommentRepository repository, UserService userService, CommentMapper commentDtoMapper) {
        this.repository = repository;
        this.userService = userService;
        this.commentDtoMapper = commentDtoMapper;
    }

    @Transactional(readOnly = true)
    public Comment findByIdAndStatus(Long id, String status) {
        Optional<Comment> requestedComment = repository.findByIdAndStatus(id, status);
        return requestedComment.orElseThrow(EntityNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public Comment findById(Long id) {
        Optional<Comment> requestedComment = repository.findById(id);
        return requestedComment.orElseThrow(EntityNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public List<Comment> findAllComments(String status) {
        return repository.findAllCommentByStatus(status);
    }

    @Transactional
    public Comment save(Comment comment) {
        return repository.save(comment);
    }

    @Transactional
    public void deleteById(Long commentId) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Comment comment = findById(commentId);
        if (userDetails.getUsername().equals(comment.getAuthor().getLogin()) || userDetails.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority)
                .anyMatch(a -> UserRoles.ADMINISTRATOR.getAuthority().equals(a))) {
            repository.deleteById(commentId);
        } else throw new UnauthorisedDataModification();
    }

    @Transactional
    public Comment setComment(Comment comment, Long id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User seller = userService.findByIdAndStatus(id, "VERIFIED");
        User author = userService.findByLogin(userDetails.getUsername()).orElseThrow(EntityNotFoundException::new);
        if (UserRoles.SELLER.equals(seller.getRole()) && !(seller.getId().equals(author.getId()))){
            comment.setSeller(seller);
            comment.setAuthor(author);
        }else throw new UnauthorisedDataModification();
        return comment;
    }

    @Transactional
    public Comment updateComment(Long commentId, Comment newComment) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Comment comment = findById(commentId);
        Comment updatedComment = null;
        if (userDetails.getUsername().equals(comment.getAuthor().getLogin()) || userDetails.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority)
                .anyMatch(a -> UserRoles.ADMINISTRATOR.getAuthority().equals(a))) {
            updatedComment = commentDtoMapper.updateComment(comment, newComment);
        } else throw new UnauthorisedDataModification();
        return updatedComment;
    }

    @Transactional
    public Comment verifyComment(Long id){
        Comment targetComment = findByIdAndStatus(id, "NOT_VERIFIED");
        targetComment.setStatus("VERIFIED");
        return targetComment;
    }

}
