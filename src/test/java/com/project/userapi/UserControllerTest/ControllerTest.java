package com.project.userapi.UserControllerTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import org.assertj.core.api.Assertions;

import org.junit.Test;

import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.project.userapi.dao.UserDAO;
import com.project.userapi.model.User;
import com.google.gson.Gson;



@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ControllerTest {

     @Test
	void contextLoads() {
	}

    @Autowired
  private TestRestTemplate restTemplate;

    @MockBean
    private UserDAO dao;

    @Autowired
    private MockMvc mockMvc;



    @Test
    public void createdUserReturnStatus201() {

         User user = new User(1,"Guilherme", "12345698798", "32365987456", "15-05-1978", "gui@email.com");
         BDDMockito.when(dao.save(user)).thenReturn(user);
         ResponseEntity<String> responseEntity = restTemplate.postForEntity("/create/", user, String.class);
         Assertions.assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);    
       
    }
    @Test
    public void createdUserWithNameIsNullReturnStatus400() {

         User user = new User( "12345698798", "32365987456", "15-05-1978", "gui@email.com");
         BDDMockito.when(dao.save(user)).thenReturn(user);
         ResponseEntity<String> responseEntity = restTemplate.postForEntity("/create/", user, String.class);
         Assertions.assertThat(responseEntity.getStatusCodeValue()).isEqualTo(400);    
       
    }


    @Test
    public void listAllUsersReturnStatusCode200() {
         List<User> users  = Arrays.asList(new User(1,"Guilherme", "12345698798", "32365987456", "15-05-1978", "gui@email.com"),
                                           new User(2,"João", "12349998798", "32365999456", "15-05-1978", "jo@ao.com"));
     
         BDDMockito.when(dao.findAll()).thenReturn((List<User>) users);

         ResponseEntity<String> responseEntity = restTemplate.getForEntity("/read", String.class);
         Assertions.assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        
       
    }
    @Test
    public void listAllUsersReturnStatusCode500() {

         BDDMockito.when(dao.findAll()).thenReturn(null);
         ResponseEntity<String> responseEntity = restTemplate.getForEntity("/read", String.class);
         Assertions.assertThat(responseEntity.getStatusCodeValue()).isEqualTo(500);
        
       
    }
    @Test
    public void listOneUserReturnStatusCode200() {

        User user = new User(1,"Guilherme", "12345698798", "32365987456", "15-05-1978", "gui@email.com");
        

         BDDMockito.when(dao.findById(user.getId())).thenReturn(Optional.of(user));
     
         ResponseEntity<String> responseEntity = restTemplate.getForEntity("/read/{id}", String.class, user.getId());
         Assertions.assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);   
       
    }

    @Test
    public void listOneUserReturnStatusCode404() {

        User user = new User(2,"Guilherme", "12345698798", "32365987456", "15-05-1978", "gui@email.com");

         BDDMockito.when(dao.findById(user.getId())).thenReturn(Optional.of(user));

         ResponseEntity<String> responseEntity = restTemplate.getForEntity("/read/{id}", String.class,1);
         Assertions.assertThat(responseEntity.getStatusCodeValue()).isEqualTo(404);   
       
    }

   @Test
    public void updateUserReturnStatusCode200() throws Exception{
     User user = new User(1,"Guilherme", "12345698798", "32365987456", "15-05-1978", "gui@email.com");
         BDDMockito.when(dao.save(user)).thenReturn(user);
         BDDMockito.when(dao.findById(user.getId())).thenReturn(Optional.of(user));
         
           
           user.setName("Carlos");
           user.setCpf("25896325874");
           user.setEmail("carlos@email.com");
           
          
           Gson gson = new Gson();
           String modifyUser = gson.toJson(user);
     
            mockMvc.perform(MockMvcRequestBuilders.put("/update/{id}",1)
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .characterEncoding("UTF-8")
                                                .content(modifyUser))
                                                .andExpect(MockMvcResultMatchers.status().isOk());

    }
    @Test
    public void updateUserReturnStatusCode400() throws Exception{
     User user = new User(1,"Guilherme", "12345698798", "32365987456", "15-05-1978", "gui@email.com");
         BDDMockito.when(dao.save(user)).thenReturn(user);
         BDDMockito.when(dao.findById(user.getId())).thenReturn(Optional.of(user));
         
           
           user.setName("Carlos");
           user.setCpf("25896325874");
           user.setEmail("carlos@email.com");
           
          
           Gson gson = new Gson();
           String modifyUser = gson.toJson(user);


           System.out.println(modifyUser);
     
            mockMvc.perform(MockMvcRequestBuilders.put("/update/{id}",-1)
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .characterEncoding("UTF-8")
                                                .content(modifyUser))
                                                .andExpect(MockMvcResultMatchers.status().isNotFound());

    }

    @Test
    public void deleteUserReturnStatusCode204(){
     User user = new User(1,"Guilherme", "12345698798", "32365987456", "15-05-1978", "gui@email.com");    
    
     BDDMockito.when(dao.findById(user.getId())).thenReturn(Optional.of(user));
     BDDMockito.doNothing().when(dao).deleteById(user.getId());
     
     ResponseEntity<String> exchange = restTemplate.exchange("/delete/{id}", HttpMethod.DELETE, null, String.class, 1);
     Assertions.assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    

    }

    @Test
    public void deleteUserReturnStatusConde404(){
     User user = new User(2,"Guilherme", "12345698798", "32365987456", "15-05-1978", "gui@email.com");    
    
     BDDMockito.when(dao.findById(user.getId())).thenReturn(null);
     BDDMockito.doNothing().when(dao).deleteById(user.getId());
     
     ResponseEntity<String> exchange = restTemplate.exchange("/delete/{id}", HttpMethod.DELETE, null, String.class, 1);
     Assertions.assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

    }
    
}
