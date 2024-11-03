package com.core.basichibernate.controller;

import com.core.basichibernate.entity.Users;
import com.core.basichibernate.service.UserService;
import com.core.basichibernate.validator.UserDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
@Validated
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(value = "/register", produces = "application/json")
    public ResponseEntity<Map<String, Object>> register (@Valid @RequestBody UserDTO userDTO) {
        Users userExisting = userService.getByEmail(userDTO.getEmail());
        if (userExisting != null) {
            throw new IllegalArgumentException("Email already exists");
        }

        Users user = userService.createUser(userDTO);
        Map<String, Object> resUser = userService.getUserById(user.getId(), new String[]{"id", "name", "email", "roles"});

        return ResponseEntity.status(HttpStatus.CREATED).body(resUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getUserById(@PathVariable Long id) {
        Map<String, Object> user = userService.getUserById(id, new String[]{"id", "name", "email", "roles","profile"});
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateUser(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO) {
        Users user = userService.updateUser(id, userDTO);
        Map<String, Object> resUser = userService.getUserById(user.getId(), new String[]{"id", "name", "email", "roles"});
        return ResponseEntity.status(HttpStatus.CREATED).body(resUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        Map<String, Object> user = userService.getUserById(id, new String[] {});
        if(user.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }
}
