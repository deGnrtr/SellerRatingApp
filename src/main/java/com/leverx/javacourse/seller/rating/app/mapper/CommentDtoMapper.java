package com.leverx.javacourse.seller.rating.app.mapper;

import com.leverx.javacourse.seller.rating.app.dto.CommentCreateDto;
import com.leverx.javacourse.seller.rating.app.dto.CommentResponseDto;
import com.leverx.javacourse.seller.rating.app.entity.model.Comment;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        imports = {LocalDate.class, BigDecimal.class})
public interface CommentDtoMapper {

    @Mapping(target = "commentStatus", constant = "NOT_VERIFIED")
    @Mapping(target = "created" , expression = "java(LocalDate.now())")
    Comment toComment(CommentCreateDto commentCreateDto);

    @Mapping(target = "author", expression = "java(comment.getAuthor().getId())")
    @Mapping(target = "seller", expression = "java(comment.getSeller().getId())")
    CommentResponseDto toCommentResponseDto(Comment comment);

    List<CommentResponseDto> toCommentResponseDtoList(List<Comment> commentList);

    @Mapping(target = "id", ignore = true)
    Comment updateComment(@MappingTarget Comment comment, CommentCreateDto commentCreateDto);
}

