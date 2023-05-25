package com.electronic.store.repositories;

import com.electronic.store.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,String> {
    public User findByEmail(String email);
    public Page<User> findByNameContaining(Pageable pageable, String keywords);
}
