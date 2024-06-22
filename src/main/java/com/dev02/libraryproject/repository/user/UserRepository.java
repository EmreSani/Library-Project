package com.dev02.libraryproject.repository.user;


import com.dev02.libraryproject.entity.concretes.user.User;
import com.dev02.libraryproject.entity.enums.RoleType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;



@Repository
public interface UserRepository extends JpaRepository<User, Long> {
//    @EntityGraph(attributePaths = "roles") //rolleri çeksin diye ekledik
//    User findByEmail(String email);

    @Query("SELECT u FROM User u JOIN FETCH u.roles WHERE u.email = :username")
    User findByEmail(@Param("username") String username);

    @Query("SELECT COUNT(u) FROM User u JOIN u.roles r WHERE r.roleType = ?1")
    long countAdmin(RoleType roleType);


    boolean existsByPhone(String phone);

    boolean existsByEmail(String email);

//    //TODO: Bu JPQL gözden geçirilebilir
    @Query(value = "SELECT u FROM User u LEFT JOIN u.loanList l GROUP BY u.id ORDER BY COUNT(l) DESC")
  Page<User> findByUsersMostBorrowers(Pageable pageable);


}
