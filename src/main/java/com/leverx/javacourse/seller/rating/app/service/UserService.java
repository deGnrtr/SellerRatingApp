package com.leverx.javacourse.seller.rating.app.service;

import com.leverx.javacourse.seller.rating.app.entity.Seller;
import com.leverx.javacourse.seller.rating.app.entity.User;
import com.leverx.javacourse.seller.rating.app.entity.UserRoles;
import com.leverx.javacourse.seller.rating.app.entity.Visitor;
import com.leverx.javacourse.seller.rating.app.exception.EntityNotFoundException;
import com.leverx.javacourse.seller.rating.app.exception.UnauthorisedDataModification;
import com.leverx.javacourse.seller.rating.app.mapper.UserMapper;
import com.leverx.javacourse.seller.rating.app.repository.SellerRepository;
import com.leverx.javacourse.seller.rating.app.repository.UserRepository;
import com.leverx.javacourse.seller.rating.app.repository.VisitorRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final SellerRepository sellerRepository;
    private final VisitorRepository visitorRepository;
    private final UserRepository<User> userRepository;
    private final UserMapper userMapper;
    private final UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    public UserService(SellerRepository sellerRepository, VisitorRepository visitorRepository, UserRepository<User> userRepository, ItemService itemService, UserMapper userMapper) {
        this.sellerRepository = sellerRepository;
        this.visitorRepository = visitorRepository;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        Optional<User> requestedUser = userRepository.findById(id);
        return requestedUser.orElseThrow(EntityNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public User findByIdAndStatus(Long id, String status) {
        Optional<User> requestedUser = userRepository.findByIdAndStatus(id, status);
        return requestedUser.orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public User createUser(User newUser) {
        User createdUser = null;
        if (UserRoles.SELLER.toString().equals(newUser.getRole().toString())) {
            createdUser = sellerRepository.save((Seller) newUser);
        } else if (UserRoles.VISITOR.toString().equals(newUser.getRole().toString())) {
            createdUser = visitorRepository.save((Visitor) newUser);
        }
        return createdUser;
    }

    @Transactional
    public void deleteById(Long id) {
        if (userDetails.getUsername().equals(findById(id).getLogin()) || userDetails.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority)
                .anyMatch(a -> UserRoles.ADMINISTRATOR.getAuthority().equals(a))) {
            userRepository.deleteById(id);
        } else throw new UnauthorisedDataModification();
    }

    @Transactional(readOnly = true)
    public List<Seller> getAllSellers(String gameTitle, BigDecimal begin, BigDecimal end, String status) {
        return sellerRepository.findAllSellersByCriteria(gameTitle, begin, end, status);
    }

    @Transactional(readOnly = true)
    public List<Visitor> getAllVisitors(String status) {
        return visitorRepository.findAllVisitorByStatus(status);
    }

    @Transactional(readOnly = true)
    public Optional<User> findByLogin(String login) {

        return userRepository.findByLogin(login);
    }

    @Transactional
    public User updateUser(Long userId, User newUser) {
        User user = findById(userId);
        User updatedUser = null;
        if (userDetails.getUsername().equals(user.getLogin()) || userDetails.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority)
                .anyMatch(a -> UserRoles.ADMINISTRATOR.getAuthority().equals(a))) {
            if (user instanceof Seller){
                updatedUser = userMapper.updateSeller((Seller) user, (Seller) newUser);
            }else if (user instanceof Visitor){
                updatedUser = userMapper.updateVisitor((Visitor) user, (Visitor) newUser);
            }
        }else throw new UnauthorisedDataModification();
        return updatedUser;
    }

    @Transactional
    public User updateRating(Seller seller, BigDecimal newRating){
        BigDecimal updatedRating = seller.getRating().add(newRating).divide(new BigDecimal(seller.getAssignedComments().size()));
        seller.setRating(updatedRating);
        return updateUser(seller.getId(), seller);
    }
}
