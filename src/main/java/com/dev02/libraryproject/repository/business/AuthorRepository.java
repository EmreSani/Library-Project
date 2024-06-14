package com.dev02.libraryproject.repository.business;

import com.dev02.libraryproject.entity.concretes.business.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author,Long> {
    Page<Author> findByAuthor(Pageable pageable);

    Author findByAuthorId(Long id);
}


