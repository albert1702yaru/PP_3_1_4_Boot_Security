package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleServiceImp;
import ru.kata.spring.boot_security.demo.service.UserServiceImp;

import java.security.Principal;
import java.util.Set;

@Controller
public class UserController {
    private final UserServiceImp userService;
    private final RoleServiceImp roleServiceImp;

    @Autowired
    public UserController(UserServiceImp userService, RoleServiceImp roleService) {
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
    private String addUser(@RequestParam String username, @RequestParam String name, @RequestParam String surname,
                           @RequestParam int age, @RequestParam String password, @RequestParam Set<Role> roles) {
        userService.save(new User(name, surname, age, username, password, roles));
        return "redirect:/admin";
    }

    @PostMapping("/admin/{id}")
    public String addUpdate(@PathVariable(value = "id") long id, @RequestParam String username, @RequestParam String name, @RequestParam String surname,
                            @RequestParam int age, @RequestParam String password, @RequestParam Set<Role> roles) {
        User user = userService.findById(id).orElseThrow();
        System.out.println(id + username + name + surname + age + password + roles);
        if (!username.isEmpty()) user.setUsername(username);
        if (!name.isEmpty()) user.setName(name);
        if (!surname.isEmpty()) user.setSurname(surname);
        user.setAge(age);
        user.setPassword(password);
        user.setRoles(roles);
        System.out.println(user);
        userService.save(user);
        return "redirect:/admin";
    }

    @PostMapping("/admin/delete/{id}")
    public String userDelete(@PathVariable(value = "id") long id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }
}
