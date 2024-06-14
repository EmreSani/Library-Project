package com.dev02.libraryproject.repository.user;


import com.dev02.libraryproject.entity.concretes.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {


}
