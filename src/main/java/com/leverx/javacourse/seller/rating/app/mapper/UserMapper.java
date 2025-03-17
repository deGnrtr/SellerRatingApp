package com.leverx.javacourse.seller.rating.app.mapper;

import com.leverx.javacourse.seller.rating.app.dto.UserCreateDto;
import com.leverx.javacourse.seller.rating.app.dto.UserResponseDto;
import com.leverx.javacourse.seller.rating.app.entity.model.Seller;
import com.leverx.javacourse.seller.rating.app.entity.model.User;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.time.LocalDate;
import java.math.BigDecimal;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, imports = {LocalDate.class, BigDecimal.class},
        uses = {CommentMapper.class, ItemMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    User toUser(UserCreateDto userCreateDto);

    UserResponseDto toUserResponseDto(User user);

    List<UserResponseDto> sellerToUserResponseDtoList(List<Seller> users);

}
