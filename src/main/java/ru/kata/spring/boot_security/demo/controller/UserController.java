package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/")
    private String home(Model model) {
        return "home";
    }

    @GetMapping("/users")
    private String allUsers(Model model) {
        Iterable<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/users/{id}")
    public String userById(@PathVariable(value = "id") long id, Model model) {
        if (userService.existsById(id)) {
            Optional<User> user = userService.findById(id);
            ArrayList<User> users = new ArrayList<>();
            user.ifPresent(users::add);
            model.addAttribute("users", users);
            return "user-id";
        }
        return "redirect:/users";
    }

    @GetMapping("/users/add")
    private String userAdd(Model model) {
        return "user-add";
    }

    @PostMapping("/users/add")
    private String addUser(@RequestParam String name, @RequestParam String surname, @RequestParam String email,
                           @RequestParam String password, Model model) {
        User user = new User(name, surname, email, password);
        userService.save(user);
        return "redirect:/users";
    }

    @GetMapping("/users/{id}/edit")
    public String userEdit(@PathVariable(value = "id") long id, Model model) {
        if (userService.existsById(id)) {
            Optional<User> user = userService.findById(id);
            ArrayList<User> users = new ArrayList<>();
            user.ifPresent(users::add);
            model.addAttribute("users", users);
            return "user-edit";
        }
        return "redirect:/users";
    }

    @PostMapping("/users/{id}/edit")
    public String addUpdate(@PathVariable(value = "id") long id, @RequestParam String name, @RequestParam String surname,
                            @RequestParam String email, @RequestParam String password, Model model) {
        User user = userService.findById(id).orElseThrow();
        user.setName(name);
        user.setSurname(surname);
        user.setEmail(email);
        user.setPassword(password);
        userService.save(user);
        return "redirect:/users";
    }

    @PostMapping("/users/{id}/remove")
    public String addDelete(@PathVariable(value = "id") long id, Model model) {
        userService.deleteById(id);
        return "redirect:/users";
    }


}
