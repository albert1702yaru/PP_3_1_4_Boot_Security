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
        userServiceImp.save(new User("superadmin", "superadmin", "superadmin@mail.com",
                "superadmin", "admin",
                Set.of(roleServiceImp.findByName("ROLE_ADMIN"), roleServiceImp.findByName("ROLE_USER"))));
        userServiceImp.save(new User("admin", "admin", "admin@mail.com", "admin",
                "admin", Set.of(roleServiceImp.findByName("ROLE_ADMIN"))));
        userServiceImp.save(new User("user", "user", "user@mail.com", "user",
                "user", Set.of(roleServiceImp.findByName("ROLE_USER"))));
        userServiceImp.save(new User("sergey", "sergey", "sergey@mail.com", "sergey",
                "user", Set.of(roleServiceImp.findByName("ROLE_USER"))));
        userServiceImp.save(new User("ivan", "ivan", "ivan@mail.com", "ivan",
                "user", Set.of(roleServiceImp.findByName("ROLE_USER"))));
    }
}
