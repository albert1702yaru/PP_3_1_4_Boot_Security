package ru.kata.spring.boot_security.demo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleServiceImp;
import ru.kata.spring.boot_security.demo.service.UserServiceImp;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

@Controller
public class UserController {
    private final UserServiceImp userService;
    private final RoleServiceImp roleServiceImp;

    public UserController(UserServiceImp userService, RoleServiceImp roleService) {
        this.userService = userService;
        this.roleServiceImp = roleService;
    }

    @GetMapping("/")
    private String home() {
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
        model.addAttribute("ListRoles", roleServiceImp.findAll());
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
            model.addAttribute("ListRole", roleServiceImp.findAll());
            model.addAttribute("users", users);
            return "user-edit";
        }
        return "redirect:/";
    }

    @PostMapping("/admin/{id}/edit")
    public String addUpdate(@PathVariable(value = "id") long id, @RequestParam String username, @RequestParam String name, @RequestParam String surname,
                            @RequestParam String email, @RequestParam String password, @RequestParam Set<Role> roles) {
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
    public String userDelete(@PathVariable(value = "id") long id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }


}
