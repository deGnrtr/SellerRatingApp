package com.leverx.javacourse.seller.rating.app.repository;

import com.leverx.javacourse.seller.rating.app.entity.model.Item;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ItemRepository extends CrudRepository<Item, Long> {

}
