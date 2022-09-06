package com.bridgelabz.bookstorebackend.repository;

import com.bridgelabz.bookstorebackend.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Integer> {
    @Query(value = "SELECT * FROM mybookstore.order;", nativeQuery = true)
    List<Order> listOrder();
}
