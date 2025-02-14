package com.exam.ort.controller;

import com.exam.ort.model.UserRecord;
import com.exam.ort.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserController {

    final UserService userService;

    @GetMapping
    public List<UserRecord> getUsers() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public UserRecord getUser(@PathVariable long id) {
        return userService.findById(id);
    }

    @PostMapping("/create")
    public UserRecord createUser(@RequestBody UserRecord user) {
        return userService.save(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable long id) {
        userService.deleteById(id);
    }
}
