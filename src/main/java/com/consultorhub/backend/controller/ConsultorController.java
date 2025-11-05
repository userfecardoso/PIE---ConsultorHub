package com.consultorhub.backend.controller;

import com.consultorhub.backend.service.SeguradoraService;
import com.consultorhub.backend.dto.SeguradoraResponseDTO;

import com.consultorhub.backend.model.Cliente;
import com.consultorhub.backend.model.Consultor;
import com.consultorhub.backend.model.Seguradora;
import com.consultorhub.backend.dto.ConsultorProfileDTO;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/consultores")
public class ConsultorController {

	private final SeguradoraService seguradoraService;

    public ConsultorController(SeguradoraService seguradoraService) {
    	this.seguradoraService = seguradoraService;
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
}