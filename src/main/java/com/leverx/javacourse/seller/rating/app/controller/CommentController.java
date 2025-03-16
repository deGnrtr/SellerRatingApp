package com.leverx.javacourse.seller.rating.app.controller;

import com.leverx.javacourse.seller.rating.app.dto.CommentCreateDto;
import com.leverx.javacourse.seller.rating.app.dto.CommentResponseDto;
import com.leverx.javacourse.seller.rating.app.mapper.CommentDtoMapper;
import com.leverx.javacourse.seller.rating.app.entity.model.Comment;
import com.leverx.javacourse.seller.rating.app.service.CommentService;
import com.leverx.javacourse.seller.rating.app.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {

    private final CommentService commentService;
    private final UserService userService;
    public CommentDtoMapper commentDtoMapper;

    public CommentController(CommentService commentService, UserService userService, CommentDtoMapper commentDtoMapper) {
        this.commentService = commentService;
        this.userService = userService;
        this.commentDtoMapper = commentDtoMapper;
    }

    @PostMapping("/users/{id}/comments")
    public ResponseEntity<CommentResponseDto> saveComment(@RequestBody CommentCreateDto commentCreateDto, @PathVariable Long id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Comment newComment = commentDtoMapper.toComment(commentCreateDto);
        newComment.setSeller(userService.findById(id));
        newComment.setAuthor(userService.findByLogin(userDetails.getUsername()).get());
        return ResponseEntity.status(HttpStatus.CREATED).body(commentDtoMapper.toCommentResponseDto(commentService.save(newComment)));
    }

    @GetMapping("/users/{id}/comments")
    public ResponseEntity<List<CommentResponseDto>> getAllComments() {
        List<Comment> comments = commentService.findAllComments();
        return ResponseEntity.status(HttpStatus.FOUND).body(commentDtoMapper.toCommentResponseDtoList(comments));
    }

    @GetMapping("/comments/{id}")
    public ResponseEntity<CommentResponseDto> getComment(@PathVariable Long id) {
        Comment comment = commentService.findById(id);
        return ResponseEntity.status(HttpStatus.FOUND).body(commentDtoMapper.toCommentResponseDto(comment));
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId, @RequestParam Long authorId) {
        Comment comment = commentService.findById(commentId);
        if (comment.getAuthor().getId().equals(authorId)) {
            commentService.deleteById(commentId);
            return ResponseEntity.status(HttpStatus.OK).build();
        } else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("/comments/{id}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long commentId, @RequestBody CommentCreateDto commentCreateDto, @RequestParam Long authorId){
        Comment comment = commentService.findById(commentId);
        Comment updatedComment = commentDtoMapper.toComment(commentCreateDto);
        if (comment.getAuthor().getId().equals(authorId)){
            updatedComment.setSeller(comment.getSeller());
            updatedComment.setAuthor(comment.getAuthor());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(commentDtoMapper.toCommentResponseDto(commentService.save(updatedComment)));
        } else return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}
