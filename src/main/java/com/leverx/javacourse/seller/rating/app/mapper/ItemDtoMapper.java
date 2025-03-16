package com.leverx.javacourse.seller.rating.app.mapper;

import com.leverx.javacourse.seller.rating.app.dto.ItemCreateDto;
import com.leverx.javacourse.seller.rating.app.dto.ItemResponseDto;
import com.leverx.javacourse.seller.rating.app.entity.model.Item;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.time.LocalDate;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, imports = LocalDate.class)
public interface ItemDtoMapper {

    @Mapping(target = "created", expression = "java(LocalDate.now())")
    Item toItem(ItemCreateDto itemCreateDto);

    @Mapping(target = "seller", expression = "java(item.getSeller().getId())")
    ItemResponseDto toItemResponseDto(Item item);

    List<ItemResponseDto> toItemResponseDtoList(List<Item> itemList);

    @Mapping(target = "id", ignore = true)
    Item updateItem(@MappingTarget Item comment, ItemCreateDto itemCreateDto);
}
