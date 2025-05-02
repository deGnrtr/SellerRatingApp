package com.leverx.javacourse.seller.rating.app.mapper;

import com.leverx.javacourse.seller.rating.app.dto.UserCreateDto;
import com.leverx.javacourse.seller.rating.app.dto.UserResponseDto;
import com.leverx.javacourse.seller.rating.app.entity.Seller;

import com.leverx.javacourse.seller.rating.app.entity.Visitor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {ReviewMapper.class, ItemMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    Seller toSeller(UserCreateDto userCreateDto);

    Visitor toVisitor(UserCreateDto userCreateDto);

    UserResponseDto sellerToUserResponseDto(Seller user);

    UserResponseDto visitorToUserResponseDto(Visitor user);

    List<UserResponseDto> visitorToUserResponseDtoList (List<Visitor> visitors);

    List<UserResponseDto> sellerToUserResponseDtoList(List<Seller> users);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "assignedReviews", ignore = true)
    Seller updateSeller(@MappingTarget Seller seller, Seller newSeller);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "assignedReviews", ignore = true)
    Visitor updateVisitor(@MappingTarget Visitor visitor, Visitor newVisitor);
}
