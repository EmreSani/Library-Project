package com.dev02.libraryproject.repository.business;

import com.dev02.libraryproject.entity.concretes.business.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book,Long> {



    boolean findByNameIgnoreCase(String bookName);
}
