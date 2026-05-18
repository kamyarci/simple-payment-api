package com.kamyla.simple_payment_api.repositories;

import com.kamyla.simple_payment_api.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByDocument(String document);

    Optional<User> findUserById(Long id);

    Optional<User> findUserByEmail(String email);
}
