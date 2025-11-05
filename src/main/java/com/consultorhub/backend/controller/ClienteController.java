package com.consultorhub.backend.controller;

import com.consultorhub.backend.dto.ClienteDTO;
import com.consultorhub.backend.dto.ClienteResponseDTO;
import com.consultorhub.backend.model.Cliente;
import com.consultorhub.backend.model.Consultor;
import com.consultorhub.backend.model.Apolice;
import com.consultorhub.backend.service.ClienteService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }
 
// -----------------------------------------------------------------------------
    
    @PostMapping
    public ResponseEntity<ClienteResponseDTO> registrarNovoCliente(
            @RequestBody ClienteDTO clienteDTO, 
            @AuthenticationPrincipal Consultor consultorLogado) {
        
        try {
            Cliente clienteCriado = clienteService.criarCliente(clienteDTO, consultorLogado);
            ClienteResponseDTO dtoResponse = new ClienteResponseDTO(clienteCriado);
            return ResponseEntity.status(HttpStatus.CREATED).body(dtoResponse);
        } catch (RuntimeException e) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }
  
// -----------------------------------------------------------------------------
  
    @GetMapping
    public List<ClienteResponseDTO> getMeusClientes(
            @AuthenticationPrincipal Consultor consultorLogado) {
        
        List<Cliente> clientes = clienteService.ClientesPorConsultor(consultorLogado);
        
        return clientes.stream()
                .map(ClienteResponseDTO::new) 
                .collect(Collectors.toList());
    }
    
// -----------------------------------------------------------------------------

    @GetMapping("/{idCliente}")
    public ResponseEntity<ClienteResponseDTO> getClientePorId(
            @PathVariable UUID idCliente,
            @AuthenticationPrincipal Consultor consultorLogado) {
        try {
            Cliente cliente = clienteService.obterClientePorId(idCliente, consultorLogado);

            return ResponseEntity.ok(new ClienteResponseDTO(cliente));
        } catch (AccessDeniedException e) {
        	throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

// -----------------------------------------------------------------------------

    @PutMapping("/{idCliente}")
    public ResponseEntity<ClienteResponseDTO> atualizarCliente( 
            @PathVariable UUID idCliente,
            @RequestBody ClienteDTO clienteDTO,
            @AuthenticationPrincipal Consultor consultorLogado) {
        try {
            Cliente cliente = clienteService.atualizarCliente(idCliente, clienteDTO, consultorLogado);
           
            return ResponseEntity.ok(new ClienteResponseDTO(cliente));
        } catch (AccessDeniedException e) {
        	throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
    
 // -----------------------------------------------------------------------------

    @DeleteMapping("/{idCliente}")
    public ResponseEntity<Void> deletarCliente(
            @PathVariable UUID idCliente,
            @AuthenticationPrincipal Consultor consultorLogado) {
        try {
            clienteService.deletarCliente(idCliente, consultorLogado);
            return ResponseEntity.noContent().build(); 
        } catch (AccessDeniedException e) {
        	throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
    
// -----------------------------------------------------------------------------
    
    @GetMapping("/{idCliente}/apolices")
    public ResponseEntity<List<Apolice>> getApolicesDoCliente(
            @PathVariable UUID idCliente,
            @AuthenticationPrincipal Consultor consultorLogado) {
        try {
            List<Apolice> apolices = clienteService.apolicesCliente(idCliente, consultorLogado);
            return ResponseEntity.ok(apolices);
        } catch (AccessDeniedException e) {
        	throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
    
// -----------------------------------------------------------------------------
    
    @GetMapping(params = "seguradoraId")
    public List<ClienteResponseDTO> getClientesPorSeguradora( 
            @RequestParam UUID seguradoraId,
            @AuthenticationPrincipal Consultor consultorLogado) {
        
        List<Cliente> clientes = clienteService.obterClientesPorSeguradora(seguradoraId, consultorLogado);
        
        return clientes.stream()
                .map(ClienteResponseDTO::new)
                .collect(Collectors.toList());
    }

}