package com.ecommerce.ecom.Repository;

import com.ecommerce.ecom.Model.AppRole;
import com.ecommerce.ecom.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(AppRole appRole);
}
