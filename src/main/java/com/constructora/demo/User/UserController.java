package com.constructora.demo.User;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/employees")
public class UserController {

    final private UserService userService;

    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponse>> getAllEmployees(@RequestParam(required = false) String search) {
        return ResponseEntity.ok(userService.getAllEmployees(search));
    }

    @GetMapping("/summary")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SummaryUserResponse> summaryEmployee() {
        return ResponseEntity.ok(userService.summaryEmployees());
    }

    @PutMapping("/{id}/toggle")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> updateEmployee(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.updateEmployee(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> putMethodName(@PathVariable Integer id, @RequestBody UserUpdateRequest user) {
        return ResponseEntity.ok(userService.userUpdateInfo(id, user));
    }

}
