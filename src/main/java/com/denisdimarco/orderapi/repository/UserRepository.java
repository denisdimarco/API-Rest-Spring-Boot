package com.denisdimarco.orderapi.repository;

import com.denisdimarco.orderapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    //with the first word "find" in method name, spring detects that it is a search method.
    public Optional<User> findByUsername(String username);



}
