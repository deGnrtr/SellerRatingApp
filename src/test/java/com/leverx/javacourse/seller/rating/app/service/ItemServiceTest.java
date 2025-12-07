package com.leverx.javacourse.seller.rating.app.service;

import com.leverx.javacourse.seller.rating.app.entity.Item;
import com.leverx.javacourse.seller.rating.app.entity.Review;
import com.leverx.javacourse.seller.rating.app.entity.Seller;
import com.leverx.javacourse.seller.rating.app.entity.UserRoles;
import com.leverx.javacourse.seller.rating.app.entity.Visitor;
import com.leverx.javacourse.seller.rating.app.exception.EntityNotFoundException;
import com.leverx.javacourse.seller.rating.app.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTest {
    @Mock
    ItemRepository itemRepository;

    @InjectMocks
    ItemService itemService;

    private Item item;

    @BeforeEach
    public void setUp() {
        Seller seller = new Seller(1L, "Test seller", "Password", "Biba", "Boba", "test@mail.com", LocalDate.now(),
                UserRoles.SELLER, null, "VERIFIED", null, null, BigDecimal.TWO);
        Visitor visitor = new Visitor(1L, "Test visitor", "Password", "Pupa", "Lupa", "test@mail.com", LocalDate.now(),
                UserRoles.VISITOR, null, "VERIFIED");
        List<Review> reviews = List.of(new Review(1L, "First review", LocalDate.now(), "VERIFIED", BigDecimal.TEN, visitor, seller),
                new Review(2L, "Second review", LocalDate.now(), "VERIFIED", BigDecimal.TEN, visitor, seller));
        seller.setOwnReviews(reviews);
        seller.setAssignedReviews(reviews);
        visitor.setOwnReviews(reviews);
        item = new Item(1L, "Test", "Item for test", "Some game", LocalDate.now(), LocalDate.now(),
                seller);
    }

    @Test
    public void findById_ReturnsSuccessfully() {
        given(this.itemRepository.findById(1L)).willReturn(Optional.of(item));

        var requstedItem = itemService.findById(1L);

        assertNotNull(requstedItem);
        assertEquals(item, requstedItem);
        verify(itemRepository, times(1)).findById(1L);
    }

    @Test
    public void findById_FinishedWithException(){
        when(this.itemRepository.findById(2L)).thenThrow(EntityNotFoundException.class);

        assertThrows(EntityNotFoundException.class, () -> itemService.findById(2L));
    }

    @Test
    public void save_SuccessfullySaved() {
        when(this.itemRepository.save(item)).thenReturn(item);

        var savedItem = itemService.save(item);

        assertEquals(item, savedItem);
        verify(itemRepository, times(1)).save(item);
    }

}
