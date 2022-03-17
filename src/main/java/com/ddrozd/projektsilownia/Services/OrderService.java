package com.ddrozd.projektsilownia.Services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.ddrozd.projektsilownia.Entities.Client;
import com.ddrozd.projektsilownia.Entities.Employee;
import com.ddrozd.projektsilownia.Entities.Order;
import com.ddrozd.projektsilownia.Entities.Pass;
import com.ddrozd.projektsilownia.Repositories.OrderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> findAll(){
        return orderRepository.findAll();
    }

    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    public void delete(Order order) {
        orderRepository.delete(order);
    }

    public void createOrderWithCurrentDate(Client client, Employee employee, Pass pass) {
        LocalDateTime ldt = LocalDateTime.now();
        LocalDateTime edt = ldt.plusMonths(pass.getExpiryTime());
        
        Order order = new Order();
        order.setClient(client);
        order.setEmployee(employee);
        order.setPass(pass);
        order.setBoughtDate(ldt);
        order.setExpirationDate(edt);
        order.setPrice(pass.getPrice());

        orderRepository.save(order);
    }

    public void createOrderWithCurrentDate(Client client, Pass pass) {
        LocalDateTime ldt = LocalDateTime.now();
        LocalDateTime edt = ldt.plusMonths(pass.getExpiryTime());
        
        Order order = new Order();
        order.setClient(client);
        order.setPass(pass);
        order.setBoughtDate(ldt);
        order.setExpirationDate(edt);
        order.setPrice(pass.getPrice());

        orderRepository.save(order);
    }

    public List<Order> findActiveOrderByClient(Client client) {
        LocalDateTime ldt = LocalDateTime.now();
        return orderRepository.findByClientIdAndExpirationDateGreaterThanOrderByExpirationDateDesc(client.getId(), ldt);
    }
    
}
