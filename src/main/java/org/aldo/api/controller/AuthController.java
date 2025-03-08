package org.aldo.api.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.aldo.api.data.dto.AccessDto;
import org.aldo.api.data.dto.AuthRequestDto;
import org.aldo.api.data.dto.ProfessorDto;
import org.aldo.api.service.interfaces.AuthService;
import org.aldo.api.service.interfaces.ProfessorService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final ProfessorService professorService;
    private final AuthenticationManager authenticationManager;
    @GetMapping("/check-first-access")
    public ResponseEntity<AccessDto> checkFirstAccess(@RequestParam String email){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(authService.checkFirstAccess(email));
    }
    @PostMapping("/login")
    public ProfessorDto login(@RequestBody AuthRequestDto authRequestDto, HttpServletResponse response) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        authRequestDto.getEmail(), authRequestDto.getPassword()));
        String token = authService.login(authRequestDto.getEmail());
        response.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        return professorService.getProfessorDtoByEmail(authRequestDto.getEmail());
    }
    @PostMapping("/logout")
    public ResponseEntity<HttpStatus> logout(HttpServletRequest request) {
        authService.logout(request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
