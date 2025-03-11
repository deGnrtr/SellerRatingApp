package com.leverx.javacourse.seller_rating_app.entity.dto_mappers;

import com.leverx.javacourse.seller_rating_app.entity.dto.UserCreateDto;
import com.leverx.javacourse.seller_rating_app.entity.dto.UserResponseDto;
import com.leverx.javacourse.seller_rating_app.entity.model.User;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.time.LocalDate;
import java.math.BigDecimal;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, imports = {LocalDate.class, BigDecimal.class}, uses = {CommentDtoMapper.class, ItemDtoMapper.class})
public interface UserDtoMapper {

    @Mapping(target = "created" , expression = "java(LocalDate.now())")
    @Mapping(target = "rating" , expression = "java(new BigDecimal(0))")
    User toUser(UserCreateDto userCreateDto);

    UserResponseDto toUserResponseDto(User user);
}
