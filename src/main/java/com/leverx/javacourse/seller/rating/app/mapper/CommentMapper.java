package com.leverx.javacourse.seller.rating.app.mapper;

import com.leverx.javacourse.seller.rating.app.dto.CommentCreateDto;
import com.leverx.javacourse.seller.rating.app.dto.CommentResponseDto;
import com.leverx.javacourse.seller.rating.app.entity.Comment;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        imports = {LocalDate.class, BigDecimal.class})
public interface CommentMapper {

    Comment toComment(CommentCreateDto commentCreateDto);

    @Mapping(target = "author", source = "author.id")
    @Mapping(target = "seller", source = "seller.id")
    CommentResponseDto toCommentResponseDto(Comment comment);

    List<CommentResponseDto> toCommentResponseDtoList(List<Comment> commentList);

    @Mapping(target = "id", ignore = true)
    Comment updateComment(@MappingTarget Comment comment, Comment commentCreateDto);
}

