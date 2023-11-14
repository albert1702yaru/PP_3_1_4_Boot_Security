package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.Optional;

public interface UserService {
    User findByUsername(String username);

    Iterable<User> findAll();

    boolean existsById(Long id);

    Optional<User> findById(Long id);

    void save(User user);

    void deleteById(Long id);
}
