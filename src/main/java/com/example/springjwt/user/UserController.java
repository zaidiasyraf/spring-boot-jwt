package com.example.springjwt.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    private final UserRepo userRepo;

    public UserController(final UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @PostMapping(path = "/create")
    public ResponseEntity<User> createUser (@RequestBody User user) {
        return ResponseEntity.ok(userRepo.save(user));
    }

    @GetMapping(path = "/all")
    public ResponseEntity<Iterable<User>> getAllUser () {
        return ResponseEntity.ok(userRepo.findAll());
    }


}
