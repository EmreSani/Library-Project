package com.dev02.libraryproject.repository.user;


import com.dev02.libraryproject.entity.concretes.user.User;
import com.dev02.libraryproject.entity.enums.RoleType;
import com.dev02.libraryproject.service.user.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {


    boolean existsByPhone(String phone);

    boolean existByEmail(String email);

    User findByEmailEquals(String email);

    User findByUsernameEquals(String username);

    //TODO: Bu JPQL gözden geçirilebilir
    @Query(value = "SELECT u FROM User u LEFT JOIN u.loanList l GROUP BY u.id ORDER BY COUNT(l) DESC")
    Page<User> findByUsersMostBorrowers(Pageable pageable);

    @Query(value = "SELECT COUNT(u) FROM User u WHERE u.userRole.roleType = ?1")
    long countAdmin(RoleType roleType);
}
