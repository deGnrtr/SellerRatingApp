package com.leverx.javacourse.seller_rating_app.entity.dto_mappers;

import com.leverx.javacourse.seller_rating_app.entity.dto.UserCreateDto;
import com.leverx.javacourse.seller_rating_app.entity.dto.UserResponseDto;
import com.leverx.javacourse.seller_rating_app.entity.model.Seller;
import com.leverx.javacourse.seller_rating_app.entity.model.User;

import com.leverx.javacourse.seller_rating_app.entity.model.Visitor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.time.LocalDate;
import java.math.BigDecimal;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, imports = {LocalDate.class, BigDecimal.class},
        uses = {CommentDtoMapper.class, ItemDtoMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserDtoMapper {

    @Mapping(target = "created" , expression = "java(LocalDate.now())")
    @Mapping(target = "rating" , expression = "java(new BigDecimal(0))")
    @Mapping(target = "sellerStatus" , defaultValue = "not approved")
    Seller toSeller(UserCreateDto userCreateDto);

    @Mapping(target = "created" , expression = "java(LocalDate.now())")
    Visitor toVisitor(UserCreateDto userCreateDto);

    //UserResponseDto sellerToUserResponseDto(Seller user);

    UserResponseDto toUserResponseDto(User user);

    List<UserResponseDto> sellerToUserResponseDtoList(List<Seller> users);

}
