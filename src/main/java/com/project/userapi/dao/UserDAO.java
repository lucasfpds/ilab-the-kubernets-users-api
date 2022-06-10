package com.project.userapi.dao;


import org.springframework.data.repository.CrudRepository;

import com.project.userapi.model.User;

public interface UserDAO extends CrudRepository<User, Integer> {
    
}
