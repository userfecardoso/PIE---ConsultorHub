package com.consultorhub.backend.controller;

import com.consultorhub.backend.dto.SeguradoraDTO;
import com.consultorhub.backend.dto.SeguradoraResponseDTO;
import com.consultorhub.backend.model.Consultor;
import com.consultorhub.backend.model.Seguradora;
import com.consultorhub.backend.service.SeguradoraService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/seguradoras")
public class SeguradoraController {

    private final SeguradoraService seguradoraService;

    public SeguradoraController(SeguradoraService seguradoraService) {
        this.seguradoraService = seguradoraService;
    }

// ------------------------------------------------------------------
    
    @PostMapping
    public ResponseEntity<SeguradoraResponseDTO> criarSeguradora(
            @RequestBody SeguradoraDTO dto,
            @AuthenticationPrincipal Consultor consultorLogado) {
        
    	try {
    		Seguradora seguradoraCriada = seguradoraService.criarSeguradora(dto, consultorLogado);

    		return ResponseEntity.status(HttpStatus.CREATED).body(new SeguradoraResponseDTO(seguradoraCriada));    		
    	} catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

 // ------------------------------------------------------------------

    
    @GetMapping
    public List<SeguradoraResponseDTO> getMinhasSeguradoras(
            @AuthenticationPrincipal Consultor consultorLogado) {
    	
        return seguradoraService.obterMinhasSeguradoras(consultorLogado).stream()
        		.map(SeguradoraResponseDTO::new) 
        		.collect(Collectors.toList());
    }

 // ------------------------------------------------------------------

    @GetMapping("/{id}")
    public ResponseEntity<SeguradoraResponseDTO> getSeguradoraPorId(
            @PathVariable UUID id,
            @AuthenticationPrincipal Consultor consultorLogado) {
        try {
            Seguradora seguradora = seguradoraService.obterSeguradoraPorId(id, consultorLogado);

            return ResponseEntity.ok(new SeguradoraResponseDTO(seguradora));
        } catch (AccessDeniedException e) {
        	throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

 // ------------------------------------------------------------------

    @PutMapping("/{id}")
    public ResponseEntity<SeguradoraResponseDTO> atualizarSeguradora( 
            @PathVariable UUID id,
            @RequestBody SeguradoraDTO dto,
            @AuthenticationPrincipal Consultor consultorLogado) {
        try {
            Seguradora seguradora = seguradoraService.atualizarSeguradora(id, dto, consultorLogado);

            return ResponseEntity.ok(new SeguradoraResponseDTO(seguradora));
        } catch (AccessDeniedException e) {
        	throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

 // ------------------------------------------------------------------

    @DeleteMapping("/{id}")
    public ResponseEntity<SeguradoraResponseDTO> deletarSeguradora(
            @PathVariable UUID id,
            @AuthenticationPrincipal Consultor consultorLogado) {
        try {
            Seguradora seguradora = seguradoraService.deletarSeguradora(id, consultorLogado);
            
            return ResponseEntity.ok(new SeguradoraResponseDTO(seguradora)); 
        } catch (AccessDeniedException e) {
        	throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
    
 // ------------------------------------------------------------------

}