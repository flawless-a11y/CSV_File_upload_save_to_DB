package com.shopping.merchant.catalogue.repository;

import com.shopping.merchant.catalogue.entity.ERole;
import com.shopping.merchant.catalogue.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}