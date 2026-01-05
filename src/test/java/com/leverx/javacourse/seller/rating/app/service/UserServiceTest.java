package com.leverx.javacourse.seller.rating.app.service;

import com.leverx.javacourse.seller.rating.app.dto.UserCreateDto;
import com.leverx.javacourse.seller.rating.app.entity.Review;
import com.leverx.javacourse.seller.rating.app.entity.Seller;
import com.leverx.javacourse.seller.rating.app.entity.User;
import com.leverx.javacourse.seller.rating.app.entity.UserRoles;
import com.leverx.javacourse.seller.rating.app.entity.Visitor;
import com.leverx.javacourse.seller.rating.app.exception.EntityNotFoundException;
import com.leverx.javacourse.seller.rating.app.mapper.UserMapper;
import com.leverx.javacourse.seller.rating.app.repository.SellerRepository;
import com.leverx.javacourse.seller.rating.app.repository.UserRepository;
import com.leverx.javacourse.seller.rating.app.repository.VisitorRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private SellerRepository sellerRepository;
    @Mock
    private VisitorRepository visitorRepository;
    @Mock
    private UserRepository<User> userRepository;
    @Mock
    private UserMapper userMapper;
    private UserService userService;
    User seller;

    User visitor;


    @BeforeEach
    void setUp() {
        userService = new UserService(sellerRepository, visitorRepository, userRepository, userMapper);
        seller = new Seller(1L, "Test seller", "Password", "Biba", "Boba", "test@mail.com", LocalDate.now(),
                UserRoles.SELLER, null, "VERIFIED", null, null, BigDecimal.TWO);
        visitor = new Visitor(1L, "Test visitor", "Password", "Pupa", "Lupa", "test@mail.com", LocalDate.now(),
                UserRoles.VISITOR, null, "VERIFIED");
        var review = new Review(1L, "First review", LocalDate.now(), "VERIFIED", BigDecimal.TEN, visitor, seller);
        ((Seller) seller).setAssignedReviews(List.of(review));
    }

    @Test
    void findById_Successfully() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(seller));

        var requestedUser = userService.findById(1L);

        assertEquals(seller, requestedUser);
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void findById_FinishedWithException() {
    EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> userService.findById(2L));
    assertEquals("No user found matching request!", exception.getMessage());
    }

    @Test
    void findByIdAndStatus_Successfully() {
        when(userRepository.findByIdAndStatus(3L, "VERIFIED")).thenReturn(Optional.of(seller));

        var requestedUser = userService.findByIdAndStatus(3L, "VERIFIED");

        assertEquals(seller, requestedUser);
        verify(userRepository, times(1)).findByIdAndStatus(3L, "VERIFIED");
    }

    @Test
    void findByIdAndStatus_FinishedWithException() {
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> userService.findByIdAndStatus(2L, "VERIFIED"));
        assertEquals("No user found matching request!", exception.getMessage());
    }

    @Test
    void findByLogin_Successfully() {
        when(userRepository.findByLogin(eq("Biba"))).thenReturn(Optional.of(seller));

        var requestedUser = userService.findByLogin("Biba");

        assertEquals(seller, requestedUser);
        verify(userRepository, times(1)).findByLogin("Biba");
    }

    @Test
    void findByLogin_FinishedWithException() {
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> userService.findByLogin("Pupa"));
        assertEquals("Can't find specified user \"Pupa\"", exception.getMessage());
    }
    @Test
    void getAllSellers_Successfully() {
        when(sellerRepository.findAllSellersByCriteria("gameTitle", new BigDecimal(1), new BigDecimal(7), "VERIFIED")).thenReturn(List.of((Seller) seller));

        var requestedSeller = userService.getAllSellers("gameTitle", new BigDecimal(1), new BigDecimal(7), "VERIFIED").getFirst();

        assertEquals(seller, requestedSeller);
        verify(sellerRepository, times(1)).findAllSellersByCriteria("gameTitle", new BigDecimal(1), new BigDecimal(7), "VERIFIED");
    }

    @Test
    void getAllVisitors_Successfully() {
        when(visitorRepository.findAllByStatus("VERIFIED")).thenReturn(List.of((Visitor) visitor));

        var requestedVisitor = userService.getAllVisitors("VERIFIED").getFirst();

        assertEquals(visitor, requestedVisitor);
        verify(visitorRepository, times(1)).findAllByStatus("VERIFIED");
    }

    @Test
    void createSeller_Successfully() {
        UserCreateDto sellerToCreate = new UserCreateDto();
        sellerToCreate.setRole("SELLER");
        when(userMapper.toSeller(sellerToCreate)).thenReturn((Seller) seller);
        when(sellerRepository.save((Seller) seller)).thenReturn((Seller) seller);

        var savedUser = userService.createUser(sellerToCreate);

        assertEquals("SELLER", savedUser.getRole().getAuthority());
        verify(sellerRepository, times(1)).save(isA(Seller.class));
    }

    @Test
    void createVisitor_Successfully() {
        UserCreateDto visitorToCreate = new UserCreateDto();
        visitorToCreate.setRole("VISITOR");
        when(userMapper.toVisitor(visitorToCreate)).thenReturn((Visitor) visitor);
        when(visitorRepository.save((Visitor) visitor)).thenReturn((Visitor) visitor);

        var savedUser = userService.createUser(visitorToCreate);

        assertEquals("VISITOR", savedUser.getRole().getAuthority());
        verify(visitorRepository, times(1)).save(isA(Visitor.class));
    }

    @Test
    void verifyUser_Successfully() {
        User unverifyedUser = new Seller(4L, "New seller", "Password", "Cheeke", "Breeke", "test@mail.com", LocalDate.now(),
                UserRoles.SELLER, null, "NOT_VERIFIED", null, null, BigDecimal.TWO);
        doReturn(Optional.of(unverifyedUser)).when(userRepository).findByIdAndStatus(4L, "NOT_VERIFIED");
        userService.verifyUser(4L);

        assertEquals("VERIFIED", unverifyedUser.getStatus());
    }
}