package com.leverx.javacourse.seller.rating.app.service;

import com.leverx.javacourse.seller.rating.app.dto.UserCreateDto;
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
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final SellerRepository sellerRepository;
    private final VisitorRepository visitorRepository;
    private final UserRepository<User> userRepository;
    private final UserMapper userMapper;

    public UserService(SellerRepository sellerRepository, VisitorRepository visitorRepository, UserRepository<User> userRepository,  UserMapper userMapper) {
        this.sellerRepository = sellerRepository;
        this.visitorRepository = visitorRepository;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        Optional<User> requestedUser = userRepository.findById(id);
        return requestedUser.orElseThrow(() -> new EntityNotFoundException("No user found matching request!"));
    }

    @Transactional(readOnly = true)
    public User findByIdAndStatus(Long id, String status) {
        Optional<User> requestedUser = userRepository.findByIdAndStatus(id, status);
        return requestedUser.orElseThrow(() -> new EntityNotFoundException("No user found matching request!"));
    }

    @Transactional
    public User createUser(UserCreateDto newUser) {
        User createdUser = null;
        if ("SELLER".equals(newUser.getRole())) {
            createdUser = sellerRepository.save(userMapper.toSeller(newUser));
        } else if ("VISITOR".equals(newUser.getRole())) {
            createdUser = visitorRepository.save(userMapper.toVisitor(newUser));
        }
        return createdUser;
    }

    @Transactional
    public void deleteById(Long id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userDetails.getUsername().equals(findById(id).getLogin()) || userDetails.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority)
                .anyMatch(a -> UserRoles.ADMINISTRATOR.getAuthority().equals(a))) {
            userRepository.deleteById(id);
        } else throw new UnauthorisedDataModification("Lack of rights.");
    }

    @Transactional(readOnly = true)
    public List<Seller> getAllSellers(String gameTitle, BigDecimal begin, BigDecimal end, String status) {
        return sellerRepository.findAllSellersByCriteria(gameTitle, begin, end, status);
    }

    @Transactional(readOnly = true)
    public List<Visitor> getAllVisitors(String status) {
        return visitorRepository.findAllByStatus(status);
    }

    @Transactional(readOnly = true)
    public User findByLogin(String login) {
        return userRepository.findByLogin(login).orElseThrow(() -> new EntityNotFoundException("Can't find specified user \"" + login + "\""));
    }

    @Transactional
    public User updateUser(Long userId, User newUser) {
        User updatedUser = null;
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userDetails.getUsername().equals(newUser.getLogin()) || userDetails.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority)
                .anyMatch(a -> UserRoles.ADMINISTRATOR.getAuthority().equals(a))) {
            if (newUser instanceof Seller){
                updatedUser = userMapper.updateSeller(sellerRepository.findById(userId).get(), (Seller) newUser);
            }else if (newUser instanceof Visitor){
                updatedUser = userMapper.updateVisitor(visitorRepository.findById(userId).get(), (Visitor) newUser);
            }
        }else throw new UnauthorisedDataModification("Lack of rights.");
        return updatedUser;
    }

    @Transactional
    public void calculateRating(Seller seller, BigDecimal newRating){
        long verifiedReviews = seller.getAssignedReviews().stream().filter(r -> r.getStatus()
                .equals("VERIFIED")).count();
        BigDecimal verifiedSum = seller.getRating().multiply(BigDecimal.valueOf(verifiedReviews));
        BigDecimal updatedRating = verifiedSum.add(newRating).divide(BigDecimal.valueOf(verifiedReviews + 1), 2, RoundingMode.HALF_UP);
        seller.setRating(updatedRating);
        updateUser(seller.getId(), seller);
    }

    @Transactional
    public void updateRating(Seller seller, BigDecimal oldRating, BigDecimal newRating){
        long verifiedReviews = seller.getAssignedReviews().stream().filter(r -> r.getStatus()
                .equals("VERIFIED")).count();
        BigDecimal oldVerifiedSum = seller.getRating().multiply(BigDecimal.valueOf(verifiedReviews));
        BigDecimal newVerifiedSum = oldVerifiedSum.subtract(oldRating).add(newRating);
        BigDecimal updatedRating = newVerifiedSum.divide(BigDecimal.valueOf(verifiedReviews), 2, RoundingMode.HALF_UP);
        seller.setRating(updatedRating);
        updateUser(seller.getId(), seller);
    }

    @Transactional
    public User verifyUser(Long id){
        User targetUser = findByIdAndStatus(id, "NOT_VERIFIED");
        targetUser.setStatus("VERIFIED");
        return targetUser;
    }
}
