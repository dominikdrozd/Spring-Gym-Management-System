package com.ddrozd.projektsilownia.Repositories;

import java.time.LocalDateTime;
import java.util.List;

import com.ddrozd.projektsilownia.Entities.Order;

import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
    
    public List<Order> findAll();

    public List<Order> findByClientIdAndExpirationDateGreaterThanOrderByExpirationDateDesc(Long id, LocalDateTime ldt);

}
