package com.leverx.javacourse.seller.rating.app.service;

import com.leverx.javacourse.seller.rating.app.entity.Administrator;
import com.leverx.javacourse.seller.rating.app.repository.AdministratorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AdministratorServiceTest {
    @Mock
    AdministratorRepository administratorRepository;

    @InjectMocks
    AdministratorService administratorService;

    @Test
    public void getAllAdmins_ReturnsValid() {

        var firstAdmin = new Administrator(1L, "admin1", "admin", "First", "Admin"
                , "some1@mail.com", LocalDate.now(),  null);
        var secondAdmin = new Administrator(1L, "admin2", "admin", "Second", "Admin"
                , "some2@mail.com", LocalDate.now(), null);

        var admins = List.of(firstAdmin, secondAdmin);

        given(this.administratorRepository.findAll()).willReturn(admins);

        var result = administratorService.getAllAdmins();

        assertNotNull(result);
        assertEquals(admins, result);
        verify(administratorRepository, times(1)).findAll();
    }
}
