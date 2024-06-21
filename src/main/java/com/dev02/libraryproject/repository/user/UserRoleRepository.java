package com.dev02.libraryproject.repository.user;

import com.dev02.libraryproject.entity.concretes.user.Role;
import com.dev02.libraryproject.entity.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository
public interface UserRoleRepository extends JpaRepository<Role,Long> {

    @Query("SELECT r FROM UserRole r WHERE r.roleType = ?1")
    Optional<Role> findByEnumRoleEquals(RoleType roleType);

    @Query(value = "SELECT COUNT(u) FROM User u WHERE u.userRole.roleType = ?1")
    long countAdmin(RoleType roleType);

}
