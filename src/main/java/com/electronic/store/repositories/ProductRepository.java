package com.electronic.store.repositories;

import com.electronic.store.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,String> {

    public List<Product> findByProductTitleContaining(String keywords);
    public List<Product> findByProductLiveTrue();
}
