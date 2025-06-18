package com.leverx.javacourse.seller.rating.app.service;

import com.leverx.javacourse.seller.rating.app.entity.Administrator;
import com.leverx.javacourse.seller.rating.app.repository.AdministratorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdministratorService {

    private final AdministratorRepository administratorRepository;

    public AdministratorService(AdministratorRepository administratorRepository) {
        this.administratorRepository = administratorRepository;
    }

    @Transactional(readOnly = true)
    public List<Administrator> getAllAdmins (){
        return (List<Administrator>) administratorRepository.findAll();
    }
}
