package com.leverx.javacourse.seller_rating_app.entity.dto_mappers;

import com.leverx.javacourse.seller_rating_app.entity.dto.ItemCreateDto;
import com.leverx.javacourse.seller_rating_app.entity.dto.ItemResponseDto;
import com.leverx.javacourse.seller_rating_app.entity.model.Item;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.time.LocalDate;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, imports = LocalDate.class)
public interface ItemDtoMapper {

    @Mapping(target = "created", expression = "java(LocalDate.now())")
    Item toItem(ItemCreateDto itemCreateDto);

    @Mapping(target = "seller", expression = "java(item.getSeller().getId())")
    ItemResponseDto toItemResponseDto(Item item);

    List<ItemResponseDto> toItemResponseDtoList(List<Item> itemList);
}
