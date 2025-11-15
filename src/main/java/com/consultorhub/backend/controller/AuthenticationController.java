package com.consultorhub.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.consultorhub.backend.dto.auth.AuthenticationRequest;
import com.consultorhub.backend.dto.auth.AuthenticationResponse;
import com.consultorhub.backend.dto.auth.RegisterRequestDTO; 
import com.consultorhub.backend.model.Consultor; 
import com.consultorhub.backend.repository.ConsultorRepository;
import com.consultorhub.backend.service.JwtService;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/auth") 
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final ConsultorRepository consultorRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationController(AuthenticationManager authenticationManager,
                                   ConsultorRepository consultorRepository,
                                   JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.consultorRepository = consultorRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
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

    
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @Valid @RequestBody RegisterRequestDTO request
    ) {

        if (consultorRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Erro: Email já está em uso!");

        }

        Consultor novoConsultor = new Consultor();
        novoConsultor.setNome(request.getNome());
        novoConsultor.setEmail(request.getEmail());
        novoConsultor.setCpf(request.getCpf());
        
        novoConsultor.setSenha(passwordEncoder.encode(request.getSenha()));
        
        consultorRepository.save(novoConsultor);

        String jwtToken = jwtService.generateToken(novoConsultor);

        return ResponseEntity.ok(new AuthenticationResponse(jwtToken));
    }
    
}