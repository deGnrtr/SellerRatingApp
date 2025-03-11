package com.leverx.javacourse.seller_rating_app.entity.dto_mappers;

import com.leverx.javacourse.seller_rating_app.entity.dto.CommentCreateDto;
import com.leverx.javacourse.seller_rating_app.entity.dto.CommentResponseDto;
import com.leverx.javacourse.seller_rating_app.entity.model.Comment;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CommentDtoMapper {

    Comment toComment(CommentCreateDto commentCreateDto);

    CommentResponseDto toCommentResponseDto(Comment comment);

    List<CommentResponseDto> toCommentResponseDtoList(List<Comment> commentList);
}

