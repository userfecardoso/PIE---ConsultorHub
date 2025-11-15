package com.consultorhub.backend.controller;

import com.consultorhub.backend.service.SeguradoraService;
import com.consultorhub.backend.service.ConsultorService;
import com.consultorhub.backend.dto.SeguradoraResponseDTO;

import com.consultorhub.backend.model.Cliente;
import com.consultorhub.backend.model.Consultor;
import com.consultorhub.backend.model.Seguradora;
import com.consultorhub.backend.dto.ConsultorProfileDTO;
import com.consultorhub.backend.dto.ConsultorUpdateDTO;


import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/consultores")
public class ConsultorController {

	private final SeguradoraService seguradoraService;
	private final ConsultorService consultorService;

    public ConsultorController(SeguradoraService seguradoraService, ConsultorService consultorService) {
    	this.seguradoraService = seguradoraService;
    	this.consultorService = consultorService;
    }

    @GetMapping("/me")
    public ConsultorProfileDTO getMeuPerfil(@AuthenticationPrincipal Consultor consultorLogado) {
    	return new ConsultorProfileDTO(consultorLogado);
    }


    @GetMapping("/me/clientes")
    public List<Cliente> getMeusClientes(@AuthenticationPrincipal Consultor consultorLogado) {
        return consultorLogado.getClientes();
    }


    @GetMapping("/me/seguradoras")
    public List<SeguradoraResponseDTO> getMinhasSeguradoras(@AuthenticationPrincipal Consultor consultorLogado) {
    	
    	List<Seguradora> seguradoras = seguradoraService.obterMinhasSeguradoras(consultorLogado);
    	
        return seguradoras.stream()
        		.map(SeguradoraResponseDTO::new)
        		.collect(Collectors.toList());
    }
    
    @PutMapping("/me")
    public ResponseEntity<ConsultorProfileDTO> atualizarPerfil(
            @AuthenticationPrincipal Consultor consultorLogado,
            @RequestBody ConsultorUpdateDTO dto) {
        
        Consultor consultorAtualizado = consultorService.atualizarPerfil(consultorLogado, dto);
        
        return ResponseEntity.ok(new ConsultorProfileDTO(consultorAtualizado));
    }
}