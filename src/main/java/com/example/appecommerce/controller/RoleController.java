package com.example.appecommerce.controller;

import com.example.appecommerce.payload.ApiResponse;
import com.example.appecommerce.payload.RoleDto;
import com.example.appecommerce.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/role")
public class RoleController {

    @Autowired
    RoleService roleService;

    @PreAuthorize("hasAuthority('ADD_ROLE')")
    @PostMapping("/addRole")
    public HttpEntity<?> addRole(@Valid @RequestBody RoleDto roleDto) {
        ApiResponse apiResponse = roleService.addRole(roleDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    @PreAuthorize("hasAuthority('EDIT_ROLE')")
    @PutMapping("/editRole/{id}")
    public HttpEntity<?> editRole(@PathVariable UUID id, @RequestBody RoleDto roleDto) {
        ApiResponse apiResponse = roleService.editRole(id, roleDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


    @PreAuthorize("hasAuthority('DELETE_ROLE')")
    @DeleteMapping("/deleteRole/{id}")
    public HttpEntity<?> deleteRole(@PathVariable UUID id) {
    ApiResponse apiResponse = roleService.deleteRole(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PreAuthorize("hasAuthority('VIEW_ROLES')")
    @GetMapping("/getRole/{id}")
    public HttpEntity<?> getRole(@PathVariable UUID id){
        return roleService.getRole(id);
    }

    @PreAuthorize("hasAuthority('VIEW_ROLES')")
    @GetMapping("/getRoles")
    public HttpEntity<?> getRoles(){
        return roleService.getRoles();
    }
}
