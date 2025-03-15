package com.leverx.javacourse.seller_rating_app.controller;

import com.leverx.javacourse.seller_rating_app.entity.dto.ItemCreateDto;
import com.leverx.javacourse.seller_rating_app.entity.dto.ItemResponseDto;
import com.leverx.javacourse.seller_rating_app.entity.dto_mappers.ItemDtoMapper;
import com.leverx.javacourse.seller_rating_app.entity.model.Item;
import com.leverx.javacourse.seller_rating_app.service.ItemService;
import com.leverx.javacourse.seller_rating_app.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ItemController {

    private final ItemService itemService;
    private final UserService userService;
    public ItemDtoMapper itemDtoMapper;

    public ItemController(ItemService commentService, UserService userService, ItemDtoMapper commentDtoMapper) {
        this.itemService = commentService;
        this.userService = userService;
        this.itemDtoMapper = commentDtoMapper;
    }

    @PostMapping("/items/{id}")
    public ResponseEntity<ItemResponseDto> saveItem(@RequestBody ItemCreateDto itemCreateDto, @RequestParam Long sellerId) {
        Item newItem = itemDtoMapper.toItem(itemCreateDto);
        newItem.setSeller(userService.findById(sellerId));
        return ResponseEntity.status(HttpStatus.CREATED).body(itemDtoMapper.toItemResponseDto(itemService.save(newItem)));
    }

    @GetMapping("/items/all")
    public ResponseEntity<List<ItemResponseDto>> getAllItems() {
        List<Item> items = itemService.findAllItems();
        return ResponseEntity.status(HttpStatus.FOUND).body(itemDtoMapper.toItemResponseDtoList(items));
    }

    @GetMapping("/items/{id}")
    public ResponseEntity<ItemResponseDto> getItem(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.FOUND).body(itemDtoMapper.toItemResponseDto(itemService.findById(id)));
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable Long itemId, @RequestParam Long sellerId) {
        Item item = itemService.findById(itemId);
        if (item.getSeller().getId().equals(sellerId)) {
            itemService.deleteById(itemId);
            return ResponseEntity.status(HttpStatus.OK).build();
        } else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("/items/{id}")
    public ResponseEntity<ItemResponseDto> updateItem(@PathVariable Long itemId, @RequestBody ItemCreateDto itemCreateDto, @RequestParam Long sellerId){
        Item item = itemService.findById(itemId);
        Item newItem = itemDtoMapper.toItem(itemCreateDto);
        if (item.getSeller().getId().equals(sellerId)){
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(itemDtoMapper.toItemResponseDto(itemService.save(newItem)));
        } else return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}
