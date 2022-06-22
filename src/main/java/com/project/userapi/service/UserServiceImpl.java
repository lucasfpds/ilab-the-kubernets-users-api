package com.project.userapi.service;

import java.util.ArrayList;
import java.util.List;


import com.project.userapi.dao.UserDAO;
import com.project.userapi.exception.ApiRequestException;
import com.project.userapi.model.User;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserDAO dao;

    @Override
    public ResponseEntity<Object> createUser(User novo) {

        if (novo.getName() == null) {

              return ResponseEntity.status(400).body("{\"message\":\"Campo 'Name' precisa ser informado.\"}");
        }
        if (novo.getCpf() == null) {
            return ResponseEntity.status(400).body("{\"message\":\"Campo 'CPF' precisa ser informado.\"}");
        }
        if (novo.getTelephone() == null) {
            return ResponseEntity.status(400).body("{\"message\":\"Campo 'telephone' precisa ser informado.\"}");
        }
        if (novo.getEmail() == null) {
            return ResponseEntity.status(400).body("{\"message\":\"Campo 'Email' precisa ser informado.\"}");
        }
        if (novo.getBirthDate() == null) {
            return ResponseEntity.status(400).body("{\"message\":\"Campo 'birthDate' precisa ser informado.\"}");
        }
        try {
           dao.save(novo);
            return ResponseEntity.status(201).body(novo);
        } catch (Exception e) {

            throw new ApiRequestException("Não foi possível Cadastrar usuário.");
        }

    }

    @Override
    public ResponseEntity<Object> readUsers() {

        List<User> user = new ArrayList<User>();

        try {
            for (User u : dao.findAll()) {
                user.add(u);
            }
            if (user.isEmpty()) {

                return ResponseEntity.status(400).build();
            }
            return ResponseEntity.status(200).body(user);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiRequestException("Usuários não encontrados");
        }
    }

    @Override
    public ResponseEntity<Object> readByIdUser(Integer id) {

        try {
            var usuario = dao.findById(id);

            if (usuario.isEmpty()) {

                return ResponseEntity.status(404).body("{\"message\":\"Não existe usuário para o Id informado.\"}");

            }
            return ResponseEntity.status(200).body(usuario);

        } catch (Exception ex) {

            ex.getStackTrace();
            throw new ApiRequestException("Não foi possível encontrar usuário com o Id informado");
        }

    }

    @Override
    public ResponseEntity<Object> updateUser(User user, Integer id) {

        var userOptional = dao.findById(id);

        if (userOptional.isEmpty()) {

            return ResponseEntity.status(404).body("{\"message\":\"Não existe usuário para o Id informado.\"}");
        }
        if(userOptional.get().getId() == null){
            throw new ApiRequestException("{\"message\":\"Campo 'Id' é obrigatório.\"}");
        }
        try {
            User newUser = new User(userOptional.get().getId(), userOptional.get().getName(),
            userOptional.get().getCpf(), userOptional.get().getTelephone(), userOptional.get().getBirthDate(),
            userOptional.get().getEmail());
            dao.save(newUser);
            return ResponseEntity.status(200).body(newUser);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiRequestException("Não foi possível atualizar usuário");
        }
    }

    @Override
    public ResponseEntity<Object> deleteUser(Integer id) {

        var usuario = dao.findById(id);

        if (usuario.isEmpty()) {

            return ResponseEntity.status(404).body("{\"message\":\"Não existe usuário para o Id informado.\"}");
        }
        try {
            dao.deleteById(id);
            return ResponseEntity.status(204).build();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiRequestException("Não foi possível deletar usuário");
            
        }

    }

}
