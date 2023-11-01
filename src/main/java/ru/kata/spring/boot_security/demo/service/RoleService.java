package ru.kata.spring.boot_security.demo.service;

import org.springframework.data.repository.CrudRepository;
import ru.kata.spring.boot_security.demo.model.Role;

import java.util.Optional;

public interface RoleService extends CrudRepository<Role, Long> {
    Optional<Role> findByName(String name);
}