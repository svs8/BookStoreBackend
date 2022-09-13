package com.bridgelabz.bookstorebackend.repository;


import com.bridgelabz.bookstorebackend.model.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery,Integer> {
}
