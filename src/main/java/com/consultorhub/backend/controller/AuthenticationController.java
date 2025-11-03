package com.consultorhub.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.consultorhub.backend.dto.auth.AuthenticationRequest;
import com.consultorhub.backend.dto.auth.AuthenticationResponse;
import com.consultorhub.backend.repository.ConsultorRepository;
import com.consultorhub.backend.service.JwtService;

@RestController
@RequestMapping("/api/auth") 
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final ConsultorRepository consultorRepository;
    private final JwtService jwtService;

    public AuthenticationController(AuthenticationManager authenticationManager,
                                   ConsultorRepository consultorRepository,
                                   JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.consultorRepository = consultorRepository;
        this.jwtService = jwtService;
    }

    @PostMapping("/login") 
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody AuthenticationRequest request
    ) {
     
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getSenha()
            )
        );

      
        UserDetails user = consultorRepository.findByEmail(request.getEmail())
                .orElseThrow();
        
        String jwtToken = jwtService.generateToken(user);

        return ResponseEntity.ok(new AuthenticationResponse(jwtToken));
    }

    // Futuramente, botar o endpoint de Registro aqui zé
    
    
}