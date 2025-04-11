package com.leverx.javacourse.seller.rating.app.mapper;

import com.leverx.javacourse.seller.rating.app.dto.ReviewCreateDto;
import com.leverx.javacourse.seller.rating.app.dto.ReviewResponseDto;
import com.leverx.javacourse.seller.rating.app.entity.Review;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        imports = {LocalDate.class, BigDecimal.class})
public interface ReviewMapper {

    Review toComment(ReviewCreateDto reviewCreateDto);

    @Mapping(target = "author", source = "author.id")
    @Mapping(target = "seller", source = "seller.id")
    ReviewResponseDto toCommentResponseDto(Review review);

    List<ReviewResponseDto> toCommentResponseDtoList(List<Review> reviewList);

    @Mapping(target = "id", ignore = true)
    Review updateComment(@MappingTarget Review review, Review reviewCreateDto);
}

