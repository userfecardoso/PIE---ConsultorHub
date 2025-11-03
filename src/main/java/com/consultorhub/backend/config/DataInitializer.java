package com.consultorhub.backend.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.consultorhub.backend.model.Consultor;
import com.consultorhub.backend.repository.ConsultorRepository;
import java.util.Optional; 

@Component
public class DataInitializer implements CommandLineRunner {

    private final ConsultorRepository consultorRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(ConsultorRepository consultorRepository, PasswordEncoder passwordEncoder) {
        this.consultorRepository = consultorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        
        String emailTeste = "consultor@teste.com";
        
        Optional<Consultor> usuarioExistente = consultorRepository.findByEmail(emailTeste);
        
        if (usuarioExistente.isEmpty()) {
            
            System.out.println(">>> [DataInitializer] Criando usuário de teste...");
            
            Consultor consultorTeste = new Consultor();
            consultorTeste.setNome("Consultor de Teste");
            consultorTeste.setEmail(emailTeste);
            // CRIPTOGRAFA A SENHA "senha123"
            consultorTeste.setSenha(passwordEncoder.encode("senha123"));
            consultorTeste.setCpf("000.000.000-00"); 
            
            consultorRepository.save(consultorTeste);
            
            System.out.println(">>> [DataInitializer] Usuário de teste criado: " + emailTeste + " / senha: senha123");
        } else {
            System.out.println(">>> [DataInitializer] Usuário de teste (" + emailTeste + ") já existe.");
        }
    }
}