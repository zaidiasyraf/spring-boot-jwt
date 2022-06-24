package com.example.springjwt.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;

@DataJpaTest
@TestPropertySource("classpath:test.properties")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepoTest {

    private final UserRepo userRepo;
    private final TestEntityManager testEntityManager;

    @Autowired
    public UserRepoTest(final UserRepo userRepo, final TestEntityManager testEntityManager) {
        this.userRepo = userRepo;
        this.testEntityManager = testEntityManager;
    }

    @Test
    void saveUser() {
        User user = userRepo.save(new User("test", "test@test.com"));
        Assertions.assertEquals("test", user.getUsername());
        Assertions.assertEquals("test@test.com", user.getEmail());
    }

    @Test
    void getUser() {
        testEntityManager.persist(new User("test", "test@test.com"));
        User user = userRepo.findByUsername("test");
        Assertions.assertNotNull(user);
        Assertions.assertEquals("test", user.getUsername());
    }

}
