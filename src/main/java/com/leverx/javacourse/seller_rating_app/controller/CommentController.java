package com.leverx.javacourse.seller_rating_app.controller;

import com.leverx.javacourse.seller_rating_app.entity.dto.CommentCreateDto;
import com.leverx.javacourse.seller_rating_app.entity.dto.CommentResponseDto;
import com.leverx.javacourse.seller_rating_app.entity.dto_mappers.CommentDtoMapper;
import com.leverx.javacourse.seller_rating_app.entity.model.Comment;
import com.leverx.javacourse.seller_rating_app.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentController {

    public CommentService commentService;
    public CommentDtoMapper commentDtoMapper;

    public CommentController(CommentService commentService, CommentDtoMapper commentDtoMapper) {
        this.commentService = commentService;
        this.commentDtoMapper = commentDtoMapper;
    }

    @PostMapping("/users/{id}/comments")
    public ResponseEntity<CommentResponseDto> saveComment(@RequestBody CommentCreateDto commentCreateDto, @PathVariable Long id){
        Comment newComment = commentService.save(commentDtoMapper.toComment(commentCreateDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(commentDtoMapper.toCommentResponseDto(newComment));
    }


}
