package com.project.userapi.service;




import org.springframework.http.ResponseEntity;

import com.project.userapi.model.User;

public interface IUserService {
    public ResponseEntity<Object> createUser(User novo);
    public ResponseEntity<Object> readUsers ();
    public ResponseEntity<Object> readByIdUser (Integer id);
    public ResponseEntity<Object> updateUser (User user, Integer id);
    public ResponseEntity<Object> deleteUser(Integer id);
}
