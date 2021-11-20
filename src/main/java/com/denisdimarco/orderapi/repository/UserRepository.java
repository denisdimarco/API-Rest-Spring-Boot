package com.denisdimarco.orderapi.repository;

import com.denisdimarco.orderapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
