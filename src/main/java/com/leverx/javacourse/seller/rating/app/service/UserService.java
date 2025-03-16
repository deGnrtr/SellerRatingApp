package com.leverx.javacourse.seller.rating.app.service;

import com.leverx.javacourse.seller.rating.app.entity.model.Item;
import com.leverx.javacourse.seller.rating.app.entity.model.Seller;
import com.leverx.javacourse.seller.rating.app.entity.model.User;
import com.leverx.javacourse.seller.rating.app.entity.model.Visitor;
import com.leverx.javacourse.seller.rating.app.exception.EntityNotFoundException;
import com.leverx.javacourse.seller.rating.app.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService{

    private UserRepository<Seller> sellerRepository;
    private UserRepository<Visitor> visitorRepository;
    private UserRepository<User> userRepository;
    private ItemService itemService;

    public UserService(UserRepository<Seller> sellerRepository, UserRepository<Visitor> visitorRepository, UserRepository<User> userRepository, ItemService itemService) {
        this.sellerRepository = sellerRepository;
        this.visitorRepository = visitorRepository;
        this.userRepository = userRepository;
        this.itemService = itemService;
    }

    @Transactional
    public User findById(Long id) {
        Optional<User> requestedUser = userRepository.findById(id);
        return requestedUser.orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public Seller saveSeller(Seller seller) {
        return sellerRepository.save(seller);
    }

    @Transactional
    public Visitor saveVisitor(Visitor visitor) {
        return visitorRepository.save(visitor);
    }

    @Transactional
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public List<Seller> getSellerByRating() {
        return sellerRepository.findAllSellersByRating();
    }

    @Transactional
    public List<Seller> getSellersInRatingRange(BigDecimal begin, BigDecimal ends) {
        return sellerRepository.findSellerByRatingRange(begin, ends);
    }

    @Transactional
    public List<Seller> getSellersByGame(String gameTitle){
        List<Item> selectedItems = itemService.findByGameTitle(gameTitle);
        List<Seller> requestedUsers = new ArrayList<>();
        for (Item selectedItem : selectedItems) {
            requestedUsers.add((Seller) selectedItem.getSeller());
        }
        return requestedUsers;
    }

    @Transactional
    public Optional<User> findByLogin(String login) {
        return userRepository.findByLogin(login);
    }
}
