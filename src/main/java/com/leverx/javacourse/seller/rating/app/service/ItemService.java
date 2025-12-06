package com.leverx.javacourse.seller.rating.app.service;

import com.leverx.javacourse.seller.rating.app.entity.Item;
import com.leverx.javacourse.seller.rating.app.entity.UserRoles;
import com.leverx.javacourse.seller.rating.app.exception.EntityNotFoundException;
import com.leverx.javacourse.seller.rating.app.exception.UnauthorisedDataModification;
import com.leverx.javacourse.seller.rating.app.mapper.ItemMapper;
import com.leverx.javacourse.seller.rating.app.repository.ItemRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {
    private final ItemRepository repository;
    private final ItemMapper itemMapper;

    public ItemService(ItemRepository repository, ItemMapper itemMapper) {
        this.repository = repository;
        this.itemMapper = itemMapper;
    }

    @Transactional
    public Item updateItem(Long itemId, Item newItem) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Item item = findById(itemId);
        Item updatedItem;
        if (userDetails.getUsername().equals(item.getSeller().getLogin()) || userDetails.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority)
                .anyMatch(a -> UserRoles.ADMINISTRATOR.getAuthority().equals(a))) {
            updatedItem = itemMapper.updateItem(item, newItem);
        }else throw new UnauthorisedDataModification("Lack of rights.");
        return updatedItem;
    }

    @Transactional(readOnly = true)
    public Item findById(Long id) {
        Optional<Item> requestedItem = repository.findById(id);
        return requestedItem.orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public Item save(Item item) {
        return repository.save(item);
    }

    @Transactional
    public void deleteById(Long id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Item item = findById(id);
        if (userDetails.getUsername().equals(item.getSeller().getLogin()) || userDetails.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority)
                .anyMatch(a -> UserRoles.ADMINISTRATOR.getAuthority().equals(a))){
            repository.deleteById(id);
        } else throw new UnauthorisedDataModification("Lack of rights.");
    }

    @Transactional(readOnly = true)
    public List<Item> findAllItems(){
        return (List<Item>) repository.findAll();
    }
}
