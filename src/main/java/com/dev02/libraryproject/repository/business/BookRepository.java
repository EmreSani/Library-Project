package com.dev02.libraryproject.repository.business;

import com.dev02.libraryproject.entity.concretes.business.Book;
import com.dev02.libraryproject.payload.response.business.BookResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {


    @Query("SELECT b FROM Book b WHERE " +
            "(:query IS NULL OR b.name LIKE %:query% OR b.author.name LIKE %:query% OR b.isbn LIKE %:query% OR b.publisher.name LIKE %:query%) " +
            "AND (:categoryId IS NULL OR b.category.id = :categoryId) " +
            "AND (:authorId IS NULL OR b.author.id = :authorId) " +
            "AND (:publisherId IS NULL OR b.publisher.id = :publisherId)")
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

//    @Query("SELECT b FROM Book b LEFT JOIN b.loans l GROUP BY b.name ORDER BY COUNT(l) DESC")
//    Page<Book> findMostPopularBooks(Pageable pageable);

    @Query("SELECT b FROM Book b LEFT JOIN b.loans l GROUP BY b.id ORDER BY COUNT(l) DESC")
    Page<Book> findMostPopularBooks(Pageable pageable);


    @Query("SELECT COUNT(l) FROM Loan l WHERE l.book.id = :bookId")
    int getLoanCountForBook(@Param("bookId") Long bookId);

}
