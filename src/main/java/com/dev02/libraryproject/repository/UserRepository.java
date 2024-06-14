package com.dev02.libraryproject.repository;


import org.springframework.stereotype.Repository;

@Repository
public class UserRepository extends JpaRepository<User,Long> {


}
