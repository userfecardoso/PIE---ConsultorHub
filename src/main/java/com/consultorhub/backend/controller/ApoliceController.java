package com.consultorhub.backend.controller;

import com.consultorhub.backend.model.Apolice;
import com.consultorhub.backend.model.Consultor; 
import com.consultorhub.backend.service.ApoliceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal; 
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.access.AccessDeniedException;

@RestController
@RequestMapping("/api/apolice")
public class ApoliceController {

    private final ApoliceService apoliceService;

    public ApoliceController(ApoliceService apoliceService) {
        this.apoliceService = apoliceService;
    }

    @PostMapping("/upload")
    public ResponseEntity<Apolice> uploadApolice(
            @AuthenticationPrincipal Consultor consultorLogado,
            @RequestPart("file") MultipartFile file,        
            @RequestPart("idCliente") String idCliente,     
            @RequestPart("idSeguradora") String idSeguradora) {
        
        try {
            Apolice apoliceCriada = apoliceService.uploadDocument(
            	file, 
                consultorLogado, 
                idCliente, 
                idSeguradora
            );
           
            return ResponseEntity.status(HttpStatus.CREATED).body(apoliceCriada);

        } catch (AccessDeniedException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace(); 
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao processar o documento.", e);
        }
    }

}