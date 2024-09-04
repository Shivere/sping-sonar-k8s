package org.example.demospringsonar.interfaces;

import org.example.demospringsonar.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {
    // Additional query methods can be defined here
    Users findByUsername(String username);
}