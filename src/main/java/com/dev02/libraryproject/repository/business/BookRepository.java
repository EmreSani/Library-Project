package com.dev02.libraryproject.repository.business;

import com.dev02.libraryproject.entity.concretes.business.Book;
import com.dev02.libraryproject.payload.response.business.BookResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookRepository extends JpaRepository<Book,Long> {


    @Query("SELECT b FROM Book b WHERE " +
            "(b.name LIKE %:query% OR b.author.name LIKE %:query% OR b.isbn LIKE %:query% OR b.publisher.name LIKE %:query%) " +
            "AND b.category.id = :categoryId " +
            "AND b.author.id = :authorId " +
            "AND b.publisher.id = :publisherId")
    Page<Book> findAllBooks(String query,
                            Long categoryId,
                            Long authorId,
                            Long publisherId,
                            Pageable pageable);

    @Query("SELECT b FROM Book b " +
            "WHERE b.active = true " +
            "AND (b.name LIKE %:query% OR b.author.name LIKE %:query% OR b.isbn LIKE %:query% OR b.publisher.name LIKE %:query%) " +
            "AND b.category.id = :categoryId " +
            "AND b.author.id = :authorId " +
            "AND b.publisher.id = :publisherId")
    Page<Book> findAllActiveBooks(@Param("query") String query,
                                  @Param("categoryId") Long categoryId,
                                  @Param("authorId") Long authorId,
                                  @Param("publisherId") Long publisherId,
                                  Pageable pageable);



    boolean findByNameIgnoreCase(String bookName);

}
