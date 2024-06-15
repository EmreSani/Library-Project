package com.dev02.libraryproject.repository.business;


import com.dev02.libraryproject.entity.concretes.business.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
