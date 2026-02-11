package com.bank.modernize.controller;
<<<<<<< HEAD

=======
>>>>>>> origin/main
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bank.modernize.dto.AccountResponse;
import com.bank.modernize.dto.CreateUserRequest;
import com.bank.modernize.dto.UpdateUserRequest;
import com.bank.modernize.dto.UserResponse;
import com.bank.modernize.service.AccountService;
import com.bank.modernize.service.UserService;

<<<<<<< HEAD
=======
import lombok.RequiredArgsConstructor;
>>>>>>> origin/main
import java.util.List;

@RestController
@RequestMapping("/users")
<<<<<<< HEAD
=======
@RequiredArgsConstructor
>>>>>>> origin/main
public class UserController {

    private final UserService userService;
    private final AccountService accountService;

<<<<<<< HEAD
    public UserController(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

=======
>>>>>>> origin/main
    @PostMapping("/create")
    public ResponseEntity<UserResponse> createUser(
            @RequestBody CreateUserRequest request) {

        UserResponse response = userService.createUser(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
<<<<<<< HEAD

=======
>>>>>>> origin/main
    @GetMapping("/{userId}/accounts")
    public ResponseEntity<List<AccountResponse>> getAccountsByUserId(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
<<<<<<< HEAD
                accountService.getAccountsByCustomerId(userId));
    }

=======
                accountService.getAccountsByCustomerId(userId)
        );
    }
    
>>>>>>> origin/main
    @PutMapping("/{userId}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Long userId,
            @RequestBody UpdateUserRequest request) {

        return ResponseEntity.ok(
                userService.updateUser(userId, request));
    }
<<<<<<< HEAD

=======
    
>>>>>>> origin/main
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.deleteUser(id));
    }
<<<<<<< HEAD

=======
    
>>>>>>> origin/main
}
