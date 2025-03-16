package com.leverx.javacourse.seller.rating.app.controller;

import com.leverx.javacourse.seller.rating.app.dto.CommentCreateDto;
import com.leverx.javacourse.seller.rating.app.dto.CommentResponseDto;
import com.leverx.javacourse.seller.rating.app.entity.model.UserRoles;
import com.leverx.javacourse.seller.rating.app.mapper.CommentDtoMapper;
import com.leverx.javacourse.seller.rating.app.entity.model.Comment;
import com.leverx.javacourse.seller.rating.app.service.CommentService;
import com.leverx.javacourse.seller.rating.app.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;
    public CommentDtoMapper commentDtoMapper;

    public CommentController(CommentService commentService, UserService userService, CommentDtoMapper commentDtoMapper) {
        this.commentService = commentService;
        this.commentDtoMapper = commentDtoMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentResponseDto> getComment(@PathVariable Long id) {
        Comment comment = commentService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(commentDtoMapper.toCommentResponseDto(comment));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Comment comment = commentService.findById(commentId);
        if (userDetails.getUsername().equals(comment.getAuthor().getLogin()) || userDetails.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority)
                .anyMatch(a -> UserRoles.ADMINISTRATOR.getAuthority().equals(a))) {
            commentService.deleteById(commentId);
            return ResponseEntity.status(HttpStatus.OK).build();
        } else return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long commentId, @RequestBody CommentCreateDto commentCreateDto, @RequestParam Long authorId){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Comment comment = commentService.findById(commentId);
        if (userDetails.getUsername().equals(comment.getAuthor().getLogin()) || userDetails.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority)
                .anyMatch(a -> UserRoles.ADMINISTRATOR.getAuthority().equals(a))){
            Comment updatedComment = commentDtoMapper.updateComment(comment, commentCreateDto);
            return ResponseEntity.status(HttpStatus.OK).body(commentDtoMapper.toCommentResponseDto(commentService.save(updatedComment)));
        } else return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
