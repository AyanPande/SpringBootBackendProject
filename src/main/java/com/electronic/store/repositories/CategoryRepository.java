package com.electronic.store.repositories;

import com.electronic.store.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,String> {
    public List<Category> findByCategoryTitleContaining(String keywords);
}
