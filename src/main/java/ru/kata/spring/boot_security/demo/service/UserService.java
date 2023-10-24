package ru.kata.spring.boot_security.demo.service;

import org.springframework.data.repository.CrudRepository;
import ru.kata.spring.boot_security.demo.model.User;

public interface UserService extends CrudRepository<User, Long> {
}
