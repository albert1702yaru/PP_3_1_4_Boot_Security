package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
public class UserController {
    private final UserService userService;
    private final RoleService roleServiceImp;

    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleServiceImp = roleService;
    }

    @GetMapping("/admin")
    private String admin(Principal principal, Model model) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("userBy", userService.findByUsername(principal.getName()));
        model.addAttribute("roles", roleServiceImp.findAll());
        return "admin";
    }

    @GetMapping("/user")
    private String user(Principal principal, Model model) {
        model.addAttribute("userBy", userService.findByUsername(principal.getName()));
        model.addAttribute("roles", roleServiceImp.findAll());
        return "user";
    }

    @PostMapping("/admin/add")
    private String addUser(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/admin";
    }

    @PostMapping("/admin/{id}")
    public String addUpdate(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/delete/{id}")
    public String userDelete(@PathVariable(value = "id") long id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }
}
