package com.leverx.javacourse.seller_rating_app.service;

import com.leverx.javacourse.seller_rating_app.entity.model.Item;
import com.leverx.javacourse.seller_rating_app.repository.ItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ItemService {
    private ItemRepository repository;

    public ItemService(ItemRepository repository) {
        this.repository = repository;
    }

    public Item findById(int id) {
        Optional<Item> requestedItem = repository.findById(id);
        return requestedItem.orElseThrow(RuntimeException::new);
    }

    public Item save(Item item) {
        return repository.save(item);
    }

    public void deleteById(int id) {
        repository.deleteById(id);
    }

    public List<Item> findByGameTitle(String gameTitle) {
        return repository.findByGameTitle(gameTitle);
    }
}
