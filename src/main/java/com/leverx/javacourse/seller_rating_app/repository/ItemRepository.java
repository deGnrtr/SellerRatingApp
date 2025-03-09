package com.leverx.javacourse.seller_rating_app.repository;

import com.leverx.javacourse.seller_rating_app.entity.Item;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ItemRepository extends CrudRepository<Item, Integer> {
    List<Item> findByGameTitleIs(String gameTitle);
}
