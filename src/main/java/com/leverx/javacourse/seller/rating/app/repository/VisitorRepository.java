package com.leverx.javacourse.seller.rating.app.repository;

import com.leverx.javacourse.seller.rating.app.entity.Visitor;

import java.util.List;

public interface VisitorRepository extends UserRepository<Visitor> {

    //@Query("FROM Visitor v WHERE v.status = :status")
    List<Visitor> findAllByStatus(String status);

}
