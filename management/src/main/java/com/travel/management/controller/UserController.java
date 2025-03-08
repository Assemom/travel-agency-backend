package com.travel.management.controller;

import com.travel.management.model.User;
import com.travel.management.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Tag(name = "User Management", description = "APIs for managing users in the travel system")
@RestController
@RequestMapping("/api")
@ControllerAdvice
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Get all users", description = "Retrieves a list of all users. Accessible to Admin and Managers.")
    @GetMapping("/users") // to Admin and Managers
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @Operation(summary = "Get user by ID", description = "Retrieves user details based on the provided ID. Accessible to all users.")
    @GetMapping("/users/{userId}")  // to all users to manage the dashboard
    public User getUserById(@PathVariable Long userId){
        return userService.getUserById(userId);
    }

    @Operation(summary = "Add a new user", description = "Creates a new user. Accessible to Admin and Managers.")
    @PostMapping("/users")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        try {
            User createdUser = userService.addUser(user);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Update user details", description = "Updates a user's details. Accessible to all users.")
    @PutMapping("/users/{userId}") // to all users to manage the dashboard
    public ResponseEntity<String> updateTrip(@PathVariable int userId, @RequestBody User user){
        User currUser = userService.addUser(user);
        if (user != null){
            return new ResponseEntity<>("Updated successfully", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Failed to update", HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Delete user by ID", description = "Deletes a user profile. Accessible to Admin and Tourists.")
    @DeleteMapping("/users/{userId}") // to Admin and Tourist if they want to delete a profile
    public void deleteTripById(@PathVariable Long userId){
        userService.deleteById(userId);
    }
}
