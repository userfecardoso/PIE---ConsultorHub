package com.consultorhub.backend.controller;

import com.consultorhub.backend.model.Cliente;
import com.consultorhub.backend.model.Consultor;
import com.consultorhub.backend.model.Seguradora;
import com.consultorhub.backend.dto.ConsultorProfileDTO;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/consultor")
public class ConsultorController {


    public ConsultorController() {
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
    public List<Seguradora> getMinhasSeguradoras(@AuthenticationPrincipal Consultor consultorLogado) {
        return consultorLogado.getSeguradoras();
    }
}