package ru.kata.spring.boot_security.demo.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.Set;

@Service
public class InitiateUtils implements CommandLineRunner {

    private final UserServiceImp userServiceImp;
    private final RoleServiceImp roleServiceImp;

    public InitiateUtils(UserServiceImp userServiceImp, RoleServiceImp roleServiceImp) {
        this.userServiceImp = userServiceImp;
        this.roleServiceImp = roleServiceImp;
    }

    @Override
    public void run(String... args) {
        roleServiceImp.save(new Role("ROLE_ADMIN"));
        roleServiceImp.save(new Role("ROLE_USER"));
        userServiceImp.save(new User("superadmin", "superadmin", 10, "superadmin@mail.com",
                "admin",
                Set.of(roleServiceImp.findByName("ROLE_ADMIN"), roleServiceImp.findByName("ROLE_USER"))));
        userServiceImp.save(new User("admin", "admin", 11, "admin@mail.com",
                "admin", Set.of(roleServiceImp.findByName("ROLE_ADMIN"))));
        userServiceImp.save(new User("user", "user", 12, "user@mail.com",
                "user", Set.of(roleServiceImp.findByName("ROLE_USER"))));
        userServiceImp.save(new User("sergey", "sergey", 13, "sergey@mail.com",
                "sergey", Set.of(roleServiceImp.findByName("ROLE_USER"))));
        userServiceImp.save(new User("ivan", "ivan", 14, "ivan@mail.com", "ivan",
                Set.of(roleServiceImp.findByName("ROLE_USER"))));
    }
}
