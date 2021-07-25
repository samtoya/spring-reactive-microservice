package com.example.reactiveexamples.controllers;

import com.example.reactiveexamples.dto.UserDepartmentDTO;
import com.example.reactiveexamples.models.User;
import com.example.reactiveexamples.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<User> create(@RequestBody User user) {
        return userService.create(user);
    }

    @GetMapping
    public Flux<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{userId}")
    public Mono<ResponseEntity<User>> getUserById(@PathVariable Integer userId) {
        Mono<User> user = userService.findById(userId);
        return user.map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{userId}")
    public Mono<ResponseEntity<User>> updateUserById(@PathVariable Integer userId, @RequestBody User user) {
        return userService.updateUser(userId, user)
                .map(u -> ResponseEntity.ok(u))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/{userId}")
    public Mono<ResponseEntity<Void>> deleteUserById(@PathVariable Integer userId) {
        return userService.deleteUser(userId)
                .map(u -> ResponseEntity.ok().<Void>build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/age/{age}")
    public Flux<User> getUsersByAge(@PathVariable Integer age) {
        return userService.findUserByAge(age);
    }

    @PostMapping("search/id")
    public Flux<User> fetchUsersById(@RequestBody List<Integer> ids) {
        return userService.fetchUsers(ids);
    }

    @GetMapping("/{userId}/department")
    public Mono<UserDepartmentDTO> fetchUserAndDepartment(@PathVariable Integer userId) {
        return userService.fetchUserAndDepartment(userId);
    }
}
