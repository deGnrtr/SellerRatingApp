package com.leverx.javacourse.seller.rating.app.mapper;

import com.leverx.javacourse.seller.rating.app.dto.ReviewCreateDto;
import com.leverx.javacourse.seller.rating.app.dto.ReviewResponseDto;
import com.leverx.javacourse.seller.rating.app.entity.Review;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReviewMapper {

    Review toReview(ReviewCreateDto reviewCreateDto);

    @Mapping(target = "author", source = "author.id")
    @Mapping(target = "seller", source = "seller.id")
    ReviewResponseDto toReviewResponseDto(Review review);

    List<ReviewResponseDto> toReviewResponseDtoList(List<Review> reviewList);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "seller", ignore = true)
    @Mapping(target = "status", ignore = true)
    Review updateReview(@MappingTarget Review review, Review reviewCreateDto);
}

