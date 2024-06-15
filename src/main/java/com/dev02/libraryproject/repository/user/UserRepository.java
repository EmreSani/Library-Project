package com.dev02.libraryproject.repository.user;


import com.dev02.libraryproject.entity.concretes.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {


    boolean existsByPhone(String phone);

    boolean existByEmail(String email);

    User findByEmailEquals(String email);
}
