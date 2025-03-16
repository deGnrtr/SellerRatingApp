package com.leverx.javacourse.seller.rating.app.controller;

import com.leverx.javacourse.seller.rating.app.dto.ItemCreateDto;
import com.leverx.javacourse.seller.rating.app.dto.ItemResponseDto;
import com.leverx.javacourse.seller.rating.app.entity.model.Comment;
import com.leverx.javacourse.seller.rating.app.entity.model.UserRoles;
import com.leverx.javacourse.seller.rating.app.exception.EntityNotFoundException;
import com.leverx.javacourse.seller.rating.app.mapper.ItemDtoMapper;
import com.leverx.javacourse.seller.rating.app.entity.model.Item;
import com.leverx.javacourse.seller.rating.app.service.ItemService;
import com.leverx.javacourse.seller.rating.app.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;
    private final UserService userService;
    public ItemDtoMapper itemDtoMapper;

    public ItemController(ItemService commentService, UserService userService, ItemDtoMapper commentDtoMapper) {
        this.itemService = commentService;
        this.userService = userService;
        this.itemDtoMapper = commentDtoMapper;
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('SELLER')")
    public ResponseEntity<ItemResponseDto> saveItem(@RequestBody ItemCreateDto itemCreateDto) {
        Item newItem = itemDtoMapper.toItem(itemCreateDto);
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        newItem.setSeller(userService.findByLogin(userDetails.getUsername()).orElseThrow(EntityNotFoundException::new));
        return ResponseEntity.status(HttpStatus.CREATED).body(itemDtoMapper.toItemResponseDto(itemService.save(newItem)));
    }

    @GetMapping
    public ResponseEntity<List<ItemResponseDto>> getAllItems() {
        List<Item> items = itemService.findAllItems();
        return ResponseEntity.status(HttpStatus.OK).body(itemDtoMapper.toItemResponseDtoList(items));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemResponseDto> getItem(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(itemDtoMapper.toItemResponseDto(itemService.findById(id)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('SELLER', 'ADMINISTRATOR')")
    public ResponseEntity<String> deleteItem(@PathVariable Long itemId) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Item item = itemService.findById(itemId);
        if (userDetails.getUsername().equals(item.getSeller().getLogin()) || userDetails.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority)
                .anyMatch(a -> UserRoles.ADMINISTRATOR.getAuthority().equals(a))){
            itemService.deleteById(itemId);
            return ResponseEntity.status(HttpStatus.OK).build();
        } else return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('SELLER', 'ADMINISTRATOR')")
    public ResponseEntity<ItemResponseDto> updateItem(@PathVariable Long itemId, @RequestBody ItemCreateDto itemCreateDto){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Item item = itemService.findById(itemId);
        if (userDetails.getUsername().equals(item.getSeller().getLogin()) || userDetails.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority)
                .anyMatch(a -> UserRoles.ADMINISTRATOR.getAuthority().equals(a))){
            Item updatedComment = itemDtoMapper.updateItem(item, itemCreateDto);
            return ResponseEntity.status(HttpStatus.OK).build();
        } else return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
