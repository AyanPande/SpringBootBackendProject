package com.electronic.store.repositories;

import com.electronic.store.dtos.OrderConfirmationDto;
import com.electronic.store.entities.Order;
import com.electronic.store.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, String> {
    public List<Order> findByUser(User user);

    @Query(value = "SELECT * FROM orders WHERE user_id =:userId AND order_status = 'PENDING' ORDER BY order_date ASC LIMIT 1", nativeQuery = true)
    public Order updateStatusOfOrder(@Param("userId") String userId);

}
