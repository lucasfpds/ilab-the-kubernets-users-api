package com.project.userapi.UserServiceTest;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import org.junit.jupiter.api.AfterEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;


import com.project.userapi.dao.UserDAO;
import com.project.userapi.model.User;

@RunWith(SpringRunner.class)
@DataJpaTest
public class IUserServiceTest {
	
    @Autowired
    private UserDAO dao;

    @AfterEach
    public void delete(){
        dao.deleteAll();
    }


    @Test
    @Rollback(false)
    public void createUserTest (){

        User user = new User(1,"Guilherme",
                        "12345698798",
                        "32365987456",
                        "15-05-1978",
                        "gui@email.com");
        dao.save(user);
        Assertions.assertThat(user.getId()).isNotNull();
        Assertions.assertThat(user.getName()).isEqualTo("Guilherme");
        Assertions.assertThat(user.getCpf()).isEqualTo("12345698798");
        Assertions.assertThat(user.getTelephone()).isEqualTo("32365987456");
        Assertions.assertThat(user.getBirthDate()).isEqualTo("15-05-1978");
        Assertions.assertThat(user.getEmail()).isEqualTo("gui@email.com");
    }

    @Test
    @Rollback(false)
    public void readAllUserTest() {
        
        User user = new User(1,"Guilherme",
        "12345698798",
        "32365987456",
        "15-05-1978",
        "gui@email.com");
        
        User user2 = new User(2,"Carlos Alberto",
        "65254112365",
        "65877455896",
        "10-11-1984",
        "carlos@email.com");
        
        dao.save(user);
        dao.save(user2);

        List<User> usersList = new ArrayList<User>();

        for (User user3 :dao.findAll()) {
            
            usersList.add(user3);
        }
        

        Assertions.assertThat(usersList.size()).isEqualTo(2);

    }

    @Test
    @Rollback(false)
    public void readOneUserById (){

        User user = new User(1,"Guilherme",
        "12345698798",
        "32365987456",
        "15-05-1978",
        "gui@email.com");

        dao.save(user);
        var usuario = dao.findById(user.getId());
        Assertions.assertThat(usuario.get().getId()).isEqualTo(user.getId());


    }

    @Test
    @Rollback(false)
    public void updateUserTest(){

        User user = new User(1,"Guilherme",
        "12345698798",
        "32365987456",
        "15-05-1978",
        "gui@email.com");

        dao.save(user);
        user.setName("Carlos Alberto");
        user.setEmail("carlos@alberto.com");
        dao.save(user);
        Assertions.assertThat(user.getName()).isEqualTo("Carlos Alberto");
        Assertions.assertThat(user.getEmail()).isEqualTo("carlos@alberto.com");


    }
    @Test
    @Rollback(false)
    public void deleteUserTest(){

        User user = new User(1,"Guilherme",
        "12345698798",
        "32365987456",
        "15-05-1978",
        "gui@email.com");
        
        dao.save(user);
        dao.delete(user);

        Assertions.assertThat(dao.findById(user.getId())).isEmpty();


    }
    

}
