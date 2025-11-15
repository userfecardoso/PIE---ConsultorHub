// Em: controller/ApoliceController.java
package com.consultorhub.backend.controller;

import com.consultorhub.backend.model.Apolice;
import com.consultorhub.backend.model.Consultor; 
import com.consultorhub.backend.service.ApoliceService;
import com.consultorhub.backend.dto.ApoliceResponseDTO;
import com.consultorhub.backend.dto.ApoliceUpdateDTO;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal; 
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/apolices")
public class ApoliceController {

    private final ApoliceService apoliceService;

    public ApoliceController(ApoliceService apoliceService) {
        this.apoliceService = apoliceService;
    }
    
//  ------------------------------------------------------------------

    @PostMapping(value = "/upload", consumes= {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ApoliceResponseDTO> uploadApolice(
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
           
            ApoliceResponseDTO responseDto = new ApoliceResponseDTO(apoliceCriada);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);

        } catch (AccessDeniedException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace(); 
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao processar o documento.", e);
        }
    }

//  ------------------------------------------------------------------
    
    @GetMapping("/near_end")
    public List<ApoliceResponseDTO> getApolicesPertoDoFim(
            @AuthenticationPrincipal Consultor consultorLogado) {
        
        return apoliceService.obterApolicesComVencimentoProximo(consultorLogado)
                .stream()
                .map(ApoliceResponseDTO::new)
                .collect(Collectors.toList());
    }

//  ------------------------------------------------------------------
    
    @GetMapping("/doc-reports")
    public List<ApoliceResponseDTO> getRelatorioDeDocumentos(
            @AuthenticationPrincipal Consultor consultorLogado) {
        
        return apoliceService.obterApolicesPorConsultor(consultorLogado)
                .stream()
                .map(ApoliceResponseDTO::new)
                .collect(Collectors.toList());
    }
    
//  ------------------------------------------------------------------
    
    @PutMapping("/doc_update/{id}")
    public ResponseEntity<ApoliceResponseDTO> updateDocumento(
            @PathVariable UUID id,
            @RequestBody ApoliceUpdateDTO dto,
            @AuthenticationPrincipal Consultor consultorLogado) {
        try {
            Apolice apoliceAtualizada = apoliceService.atualizarApolice(id, dto, consultorLogado);
            return ResponseEntity.ok(new ApoliceResponseDTO(apoliceAtualizada));
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
//  ------------------------------------------------------------------
    
    @DeleteMapping("/doc_delete/{id}")
    public ResponseEntity<ApoliceResponseDTO> deleteDocumento(
            @PathVariable UUID id,
            @AuthenticationPrincipal Consultor consultorLogado) {
        try {
            Apolice apoliceDesativada = apoliceService.desativarApolice(id, consultorLogado);
            return ResponseEntity.ok(new ApoliceResponseDTO(apoliceDesativada));
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
//    ------------------------------------------------------------------
    
    @GetMapping("/client_docs/{idCliente}")
    public ResponseEntity<List<ApoliceResponseDTO>> getDocumentosDoCliente(
            @PathVariable UUID idCliente,
            @AuthenticationPrincipal Consultor consultorLogado) {
        try {
            List<Apolice> apolices = apoliceService.obterApolicesPorCliente(idCliente, consultorLogado);

            List<ApoliceResponseDTO> dtos = apolices.stream()
                .map(ApoliceResponseDTO::new)
                .collect(Collectors.toList());
            return ResponseEntity.ok(dtos);
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
//  ------------------------------------------------------------------

    @GetMapping("/tipo/{tipo}")
    public List<ApoliceResponseDTO> getApolicesPorTipo(
    		@AuthenticationPrincipal Consultor consultorLogado,
    		@PathVariable String tipo){
    	
    	List<Apolice> apolices = apoliceService.obterApolicesPorTipo(consultorLogado, tipo);
    	
    	return apolices.stream()
                .map(ApoliceResponseDTO::new)
                .collect(Collectors.toList());
    }
    
}