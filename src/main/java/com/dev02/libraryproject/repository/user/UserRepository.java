package com.dev02.libraryproject.repository.user;


import com.dev02.libraryproject.entity.concretes.user.User;
import com.dev02.libraryproject.entity.enums.RoleType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;



@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

//    @Query(value = "SELECT COUNT(u) FROM User u WHERE u.roles.roleType = ?1")
//    long countAdmin(RoleType roleType);

    @Query("SELECT COUNT(u) FROM User u JOIN u.roles r WHERE r.roleType = ?1")
    long countAdmin(RoleType roleType);


    boolean existsByPhone(String phone);

    boolean existsByEmail(String email);

//    //TODO: Bu JPQL gözden geçirilebilir
    @Query(value = "SELECT u FROM User u LEFT JOIN u.loanList l GROUP BY u.id ORDER BY COUNT(l) DESC")
  Page<User> findByUsersMostBorrowers(Pageable pageable);


}
