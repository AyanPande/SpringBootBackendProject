package com.electronic.store.repositories;

import com.electronic.store.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,String> {

    public Page<Product> findByProductTitleContaining(Pageable pageable,String keywords);
    public Page<Product> findByProductLiveTrue(Pageable pageable);
}
