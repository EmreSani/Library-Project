package com.dev02.libraryproject.repository.business;

import com.dev02.libraryproject.entity.concretes.business.Book;
import com.dev02.libraryproject.entity.concretes.business.Publisher;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {

}
