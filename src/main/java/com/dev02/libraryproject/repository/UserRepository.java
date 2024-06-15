package com.dev02.libraryproject.repository;

import com.dev02.libraryproject.entity.concretes.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
