package com.project.userapi.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.userapi.model.User;
import com.project.userapi.service.IUserService;

@RestController
public class UserController {
    
    @Autowired
    private IUserService service;

    @GetMapping("/")
    public ResponseEntity<String> helloWorld() {
        return ResponseEntity.ok("Welcome to User API");
    }

    @GetMapping("/read")
    public List<User> getUsers(){
        return service.readUsers();
    }

    @GetMapping("/read/{id}")
    public Optional<User> getUserById(@PathVariable Integer id){
        return service.readByIdUser(id);
    }

    @DeleteMapping("/delete/{id}")
    public Boolean deleteUser(@PathVariable Integer id){
        return service.deleteUser(id);
    }

    @PostMapping("/create/")
    public User createUser(@RequestBody User novo) {
        return service.createUser(novo);
    }

    @PutMapping("/update")
    public User updatUser (@RequestBody User user){
        return service.updateUser(user);
    }
   
}
