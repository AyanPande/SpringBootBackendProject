package com.electronic.store.repositories;

import com.electronic.store.dtos.PageableResponse;
import com.electronic.store.entities.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,String> {
    public Page<Category> findByCategoryTitleContaining(Pageable pageable, String keywords);
}
