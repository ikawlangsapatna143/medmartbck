package com.pioneers.medmartbck.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pioneers.medmartbck.NotFoundException.UserNotFoundException;
import com.pioneers.medmartbck.model.User;
import com.pioneers.medmartbck.repository.UserRepository;

@RestController
public class UserController {

    UserRepository repo;

    public UserController(UserRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return repo.findAll();
    }

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable Long id) {
        return repo.findById(id).orElseThrow(
                () -> new UserNotFoundException(id));
    }

    @PostMapping("/user/create")
    public String create(@RequestBody User newUser) {
        repo.save(newUser);
        return "A new user is added.";
    }

    @PutMapping("/user/edit/{id}")
    public User update(@PathVariable Long id, @RequestBody User newUser) {
        return repo.findById(id)
                .map(user -> {
                    user.setName(newUser.getName());
                    user.setPassword(newUser.getPassword());
                    return repo.save(user);
                }).orElseGet(
                        () -> {
                            return repo.save(newUser);
                        });

    }

    @DeleteMapping("/user/delete/{id}")
    public String delete(@PathVariable Long id){
        repo.deleteById(id);
        return "The user is deleted!";
    }

}