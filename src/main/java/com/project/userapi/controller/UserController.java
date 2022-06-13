package com.project.userapi.controller;



import com.project.userapi.model.User;
import com.project.userapi.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    
    @Autowired
    private IUserService service;

    @GetMapping("/")
    public ResponseEntity<String> helloWorld() {
        return ResponseEntity.ok("Welcome to User API");
    }

    @GetMapping("/read")
    public ResponseEntity<Object> getUsers(){
        return service.readUsers();
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable Integer id){
        return service.readByIdUser(id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Integer id){
        return service.deleteUser(id);
    }

    @PostMapping("/create/")
    public ResponseEntity<Object> createUser(@RequestBody User novo) {
        return service.createUser(novo);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updatUser (@RequestBody User user, @PathVariable Integer id){
        return service.updateUser(user, id);
    }
   
}
