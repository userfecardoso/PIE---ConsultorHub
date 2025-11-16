package com.consultorhub.backend.controller;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import jakarta.validation.Valid;

import com.consultorhub.backend.dto.auth.AuthenticationRequest;
import com.consultorhub.backend.dto.auth.AuthenticationResponse;
import com.consultorhub.backend.dto.auth.ForgotPasswordRequestDTO;
import com.consultorhub.backend.dto.auth.RegisterRequestDTO;
import com.consultorhub.backend.dto.auth.ResetPasswordRequestDTO;
import com.consultorhub.backend.model.Consultor; 
import com.consultorhub.backend.repository.ConsultorRepository;
import com.consultorhub.backend.service.JwtService;
import com.consultorhub.backend.service.EmailService; 


@RestController
@RequestMapping("/api/auth") 
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final ConsultorRepository consultorRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public AuthenticationController(AuthenticationManager authenticationManager,
                                   ConsultorRepository consultorRepository,
                                   JwtService jwtService, PasswordEncoder passwordEncoder,
                                   EmailService emailService) {
        this.authenticationManager = authenticationManager;
        this.consultorRepository = consultorRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }
    
// -----------------------------------------------------------------------------------------------

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

 // -----------------------------------------------------------------------------------------------

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @Valid @RequestBody RegisterRequestDTO request
    ) {

        if (consultorRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Erro: Email já está em uso!");

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
    
 // -----------------------------------------------------------------------------------------------

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(
            @RequestBody ForgotPasswordRequestDTO request) {
        
        Optional<Consultor> userOptional = consultorRepository.findByEmail(request.getEmail());

        if (userOptional.isPresent()) {
            Consultor consultor = userOptional.get();
            
            String token = UUID.randomUUID().toString();
            
            consultor.setResetToken(token);
            consultor.setResetTokenExpiry(LocalDateTime.now().plusHours(1));
            consultorRepository.save(consultor);
            
            try {
                emailService.enviarEmailReset(consultor.getEmail(), token);
            } catch (Exception e) {
                e.printStackTrace(); 
            }
            
           
        }

        return ResponseEntity.ok("E-mail Enviado!");
    }
    
// -----------------------------------------------------------------------------------------------

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(
            @RequestBody ResetPasswordRequestDTO request) {
        
        Consultor consultor = consultorRepository.findByResetToken(request.getToken())
            .orElseThrow(() -> new RuntimeException("Token de redefinição inválido ou não encontrado."));

        if (consultor.getResetTokenExpiry().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token de redefinição expirado.");
        }

        consultor.setSenha(passwordEncoder.encode(request.getNewPassword()));
        
        consultor.setResetToken(null);
        consultor.setResetTokenExpiry(null);
        
        consultorRepository.save(consultor);

        return ResponseEntity.ok("Senha redefinida com sucesso!");
    }
    
}