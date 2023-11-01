package ru.kata.spring.boot_security.demo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserServiceImp;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {
    private final UserServiceImp userService;
    private final RoleService roleService;

    public UserController(UserServiceImp userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/")
    private String home() {
        if (roleService.findByName("ROLE_ADMIN").isEmpty()) {
            Role roleAdmin = new Role();
            roleAdmin.setName("ROLE_ADMIN");
            roleService.save(roleAdmin);
        }
        if (roleService.findByName("ROLE_USER").isEmpty()) {
            Role roleUser = new Role();
            roleUser.setName("ROLE_USER");
            roleService.save(roleUser);
        }
        if (userService.findByUsername("admin") == null) {
            User user = new User();
            user.setUsername("admin");
            user.setPassword("admin");
            user.setRoles(List.of(roleService.findByName("ROLE_ADMIN").get(), roleService.findByName("ROLE_USER").get()));
            userService.save(user);
        }
        return "home";
    }

    @GetMapping("/user")
    private String getUser(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        ArrayList<User> users = new ArrayList<>();
        users.add(user);
        model.addAttribute("users", users);
        return "user";
    }

    @GetMapping("/admin")
    private String allUsers(Model model) {
        Iterable<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/admin/{id}")
    public String userById(@PathVariable(value = "id") long id, Model model) {
        if (userService.existsById(id)) {
            Optional<User> user = userService.findById(id);
            ArrayList<User> users = new ArrayList<>();
            user.ifPresent(users::add);
            model.addAttribute("users", users);
            return "user-id";
        }
        return "redirect:/home";
    }

    @GetMapping("/admin/add")
    private String userAdd(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("ListRoles", roleService.findAll());
        return "user-add";
    }

    @PostMapping("/admin/add")
    private String addUser(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/{id}/edit")
    public String userEdit(@PathVariable(value = "id") long id, Model model) {
        if (userService.existsById(id)) {
            Optional<User> user = userService.findById(id);
            ArrayList<User> users = new ArrayList<>();
            user.ifPresent(users::add);
            model.addAttribute("ListRole", roleService.findAll());
            model.addAttribute("users", users);
            return "user-edit";
        }
        return "redirect:/";
    }

    @PostMapping("/admin/{id}/edit")
    public String addUpdate(@PathVariable(value = "id") long id, @RequestParam String username, @RequestParam String name, @RequestParam String surname,
                            @RequestParam String email, @RequestParam String password, @RequestParam List<Role> roles, Model model) {
        User user = userService.findById(id).orElseThrow();
        user.setUsername(username);
        user.setName(name);
        user.setSurname(surname);
        user.setEmail(email);
        user.setPassword(password);
        user.setRoles(roles);
        userService.save(user);
        return "redirect:/admin";
    }

    @PostMapping("/admin/{id}/remove")
    public String userDelete(@PathVariable(value = "id") long id, Model model) {
        userService.deleteById(id);
        return "redirect:/admin";
    }


}
