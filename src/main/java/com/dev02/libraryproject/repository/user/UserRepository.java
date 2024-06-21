package com.dev02.libraryproject.repository.user;


import com.dev02.libraryproject.entity.concretes.user.User;
import com.dev02.libraryproject.entity.enums.RoleType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import com.dev02.libraryproject.entity.concretes.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    boolean existsByPhone(String phone);

    boolean existsByEmail(String email);

    User findByUsernameEquals(String username);

    //TODO: Bu JPQL gözden geçirilebilir
    @Query(value = "SELECT u FROM User u LEFT JOIN u.loanList l GROUP BY u.id ORDER BY COUNT(l) DESC")
    Page<User> findByUsersMostBorrowers(Pageable pageable);


}
