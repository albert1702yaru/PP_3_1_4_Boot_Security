package ru.kata.spring.boot_security.demo.service;

import org.springframework.data.repository.CrudRepository;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.Optional;

public interface UserService extends CrudRepository<User, Long> {

    User findByUsername(String username);

    Optional<User> findByName(String name);
}
