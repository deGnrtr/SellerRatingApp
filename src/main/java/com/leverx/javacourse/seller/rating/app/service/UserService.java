package com.leverx.javacourse.seller.rating.app.service;

import com.leverx.javacourse.seller.rating.app.entity.model.Seller;
import com.leverx.javacourse.seller.rating.app.entity.model.User;
import com.leverx.javacourse.seller.rating.app.entity.model.UserRoles;
import com.leverx.javacourse.seller.rating.app.entity.model.Visitor;
import com.leverx.javacourse.seller.rating.app.exception.EntityNotFoundException;
import com.leverx.javacourse.seller.rating.app.exception.UnauthorisedDataModification;
import com.leverx.javacourse.seller.rating.app.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class UserService{

    private final UserRepository<Seller> sellerRepository;
    private final UserRepository<Visitor> visitorRepository;
    private final UserRepository<User> userRepository;

    public UserService(UserRepository<Seller> sellerRepository, UserRepository<Visitor> visitorRepository, UserRepository<User> userRepository, ItemService itemService) {
        this.sellerRepository = sellerRepository;
        this.visitorRepository = visitorRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        Optional<User> requestedUser = userRepository.findById(id);
        return requestedUser.orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public User createUser(User newUser) {
        User createdUser = null;
        if (UserRoles.SELLER.toString().equals(newUser.getRole().toString())) {
            createdUser = sellerRepository.save((Seller)newUser);
        } else if (UserRoles.VISITOR.toString().equals(newUser.getRole().toString())) {
            createdUser = visitorRepository.save((Visitor) newUser);
        }
        return createdUser;
    }

    @Transactional
    public void deleteById(Long id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userDetails.getUsername().equals(findById(id).getLogin()) || userDetails.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority)
                .anyMatch(a -> UserRoles.ADMINISTRATOR.getAuthority().equals(a))){
            userRepository.deleteById(id);
        }else throw new UnauthorisedDataModification();
    }

    @Transactional(readOnly = true)
    public List<Seller> getAllSellers (String gameTitle, BigDecimal begin, BigDecimal end){
        return sellerRepository.findAllSellersByCriteria(gameTitle, begin, end);
    }

    @Transactional(readOnly = true)
    public Optional<User> findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

}
