package org.aldo.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.aldo.api.data.dto.RequestSetPasswordDto;
import org.aldo.api.service.interfaces.PasswordSetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/password-set")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class PasswordSetController {
    private final PasswordSetService passwordSetService;

    @GetMapping("/init-set-password")
    public ResponseEntity<HttpStatus> initSetPassword(@RequestParam String email) {
        passwordSetService.initiateSetPassword(email);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @PostMapping("/set-password")
    public ResponseEntity<HttpStatus> setPassword(@Valid @RequestBody RequestSetPasswordDto requestSetPasswordDto) {
        passwordSetService.setPassword(requestSetPasswordDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
