package com.example.springjwt.user;

import java.util.List;
import javax.transaction.Transactional;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("classpath:test.properties")
@Transactional
class UserIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepo userRepo;

    @Test
    void createUser() throws Exception {
        User user = new User("test", "test@test.com");
        user.setPassword("password");
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(user)))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .is2xxSuccessful());

        User createdUser = userRepo.findByUsername(user.getUsername());
        Assertions.assertNotNull(createdUser);
        Assertions.assertNotEquals(user.getPassword(), createdUser.getPassword());
        Assertions.assertEquals(user.getEmail(), createdUser.getEmail());
    }

    @Test
    void login() throws Exception {
        createUser();
        User user = new User("test", "test@test.com");
        user.setPassword("password");
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(user)))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .is2xxSuccessful())
                .andReturn();
        String responseBody = mvcResult
                .getResponse()
                .getContentAsString();
        Assertions.assertTrue(responseBody.contains(user.getUsername()));
    }

    @Test
    void getAllUser() throws Exception {
        createUser();
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/user/all")
                        .header(HttpHeaders.AUTHORIZATION, getAuthToken()))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .is2xxSuccessful())
                .andReturn();
        List<User> users = objectMapper.readValue(mvcResult
                .getResponse()
                .getContentAsString(), new TypeReference<List<User>>() {
        });
        Assertions.assertNotNull(users);
        Assertions.assertFalse(users.isEmpty());
        Assertions.assertEquals("test",
                users
                        .get(0)
                        .getUsername());
    }

    private String getAuthToken() throws Exception {
        User user = new User("test", "test@test.com");
        user.setPassword("password");
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(user)))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .is2xxSuccessful())
                .andReturn();
        return mvcResult
                .getResponse()
                .getContentAsString()
                .replace(user
                        .getUsername()
                        .concat(" "), "Bearer ");
    }

}
