package com.rudyk.catalog.repositories;

import com.rudyk.catalog.domain.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, String> {}