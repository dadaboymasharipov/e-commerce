package com.example.appecommerce.controller;

import com.example.appecommerce.payload.ApiResponse;
import com.example.appecommerce.payload.BlockUserDto;
import com.example.appecommerce.payload.UserDto;
import com.example.appecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @PreAuthorize("hasAuthority('EDIT_MY_USER')")
    @PutMapping("/editUser/{id}")
    public HttpEntity<?> editUser(@PathVariable UUID id, @RequestBody UserDto userDto) {
        ApiResponse apiResponse = userService.editUser(id, userDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PreAuthorize("hasAuthority('DELETE_USER')")
    @DeleteMapping("/deleteUser/{id}")
    public HttpEntity<?> deleteUser(@PathVariable UUID id) {
        ApiResponse apiResponse = userService.deleteUser(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PreAuthorize("hasAuthority('BLOCK_USER')")
    @DeleteMapping("/blockUser")
    public HttpEntity<?> blockUser(@RequestBody BlockUserDto blockedUserDto) {
        ApiResponse apiResponse = userService.blockUser(blockedUserDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PreAuthorize("hasAuthority('UNBLOCK_USER')")
    @GetMapping("/unblockUser")
    public HttpEntity<?> unblockUser(@RequestParam String email) {
        ApiResponse apiResponse = userService.unblockUser(email);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PreAuthorize("hasAuthority('GET_BLOCKED_USERS')")
    @GetMapping("/getBlockedUsers")
    public HttpEntity<?> getBlockedUsers(@RequestParam Integer pageNumber, @RequestParam Integer pageSize) {
        return userService.getBlockedUsers(pageNumber, pageSize);
    }

    @PreAuthorize("hasAuthority('CAN_ADD_ROLE_TO_USER')")
    @PutMapping("/assignRoleToUser")
    public HttpEntity<?> addRoleToUser(@RequestParam UUID userId, @RequestParam UUID roleId) {
        ApiResponse apiResponse = userService.addRoleToUser(userId, roleId);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/verify")
    public HttpEntity<?> verifyEmail(@RequestParam UUID id, @RequestParam String email, @RequestParam String emailCode) {
        ApiResponse apiResponse = userService.verify(id, email, emailCode);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }
}
