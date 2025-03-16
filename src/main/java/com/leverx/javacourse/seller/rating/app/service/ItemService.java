package com.leverx.javacourse.seller.rating.app.service;

import com.leverx.javacourse.seller.rating.app.entity.model.Item;
import com.leverx.javacourse.seller.rating.app.exception.EntityNotFoundException;
import com.leverx.javacourse.seller.rating.app.repository.ItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {
    private final ItemRepository repository;

    public ItemService(ItemRepository repository) {
        this.repository = repository;
    }

    @Transactional
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
        repository.deleteById(id);
    }

    @Transactional
    public List<Item> findByGameTitle(String gameTitle) {
        return repository.findByGameTitle(gameTitle);
    }

    @Transactional
    public List<Item> findAllItems(){
        return (List<Item>) repository.findAll();
    }
}
