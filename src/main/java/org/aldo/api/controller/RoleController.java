package org.aldo.api.controller;

import lombok.RequiredArgsConstructor;
import org.aldo.api.service.interfaces.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/role")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;
    @PostMapping
    public ResponseEntity<HttpStatus> createRole(@RequestBody String roleName) {
        roleService.createRole(roleName);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();

    }
}
