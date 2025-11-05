package com.consultorhub.backend.service;

import com.consultorhub.backend.dto.SeguradoraDTO;
import com.consultorhub.backend.model.Consultor;
import com.consultorhub.backend.model.Seguradora;
import com.consultorhub.backend.repository.ConsultorRepository;
import com.consultorhub.backend.repository.SeguradoraRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; 

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SeguradoraService {

    private final SeguradoraRepository seguradoraRepository;
    private final ConsultorRepository consultorRepository;

    public SeguradoraService(SeguradoraRepository seguradoraRepository, 
                             ConsultorRepository consultorRepository) {
        this.seguradoraRepository = seguradoraRepository;
        this.consultorRepository = consultorRepository;
    }


    @Transactional 
    public Seguradora criarSeguradora(SeguradoraDTO dto, Consultor consultorLogado) {
       
        Seguradora novaSeguradora = new Seguradora();
        novaSeguradora.setNome(dto.getNome());
        novaSeguradora.setEmail(dto.getEmail());
        novaSeguradora.setCnpj(dto.getCnpj());
        Seguradora seguradoraSalva = seguradoraRepository.save(novaSeguradora);

        Consultor consultor = consultorRepository.findById(consultorLogado.getId())
            .orElseThrow(() -> new RuntimeException("Consultor não encontrado"));

        consultor.getSeguradoras().add(seguradoraSalva);
        
        consultorRepository.save(consultor);

        return seguradoraSalva;
    }
    @Transactional(readOnly = true)
    public List<Seguradora> obterMinhasSeguradoras(Consultor consultorLogado) {

        Consultor consultor = consultorRepository.findById(consultorLogado.getId())
            .orElseThrow(() -> new RuntimeException("Consultor não encontrado"));

        return consultor.getSeguradoras().stream()
            .filter(seguradora -> "ATIVO".equals(seguradora.getStatus()))
            .collect(Collectors.toList());
    }
    
    public Seguradora obterSeguradoraPorId(UUID id, Consultor consultorLogado) {

        return validarEAcharSeguradora(id, consultorLogado);
    }

    public Seguradora atualizarSeguradora(UUID id, SeguradoraDTO dto, Consultor consultorLogado) {

        Seguradora seguradora = validarEAcharSeguradora(id, consultorLogado);

        seguradora.setNome(dto.getNome());
        seguradora.setEmail(dto.getEmail());
        seguradora.setCnpj(dto.getCnpj());
        
        return seguradoraRepository.save(seguradora);
    }

    public Seguradora deletarSeguradora(UUID id, Consultor consultorLogado) {

        Seguradora seguradora = validarEAcharSeguradora(id, consultorLogado);

        seguradora.setStatus("INATIVO");
        
        return seguradoraRepository.save(seguradora);
    }


    private Seguradora validarEAcharSeguradora(UUID idSeguradora, Consultor consultorLogado) {

        boolean isAssociado = seguradoraRepository.existsByIdAndConsultoresId(
            idSeguradora, 
            consultorLogado.getId()
        );
        
        if (!isAssociado) {
            throw new AccessDeniedException("Acesso Negado: Você não está associado a esta seguradora.");
        }

        return seguradoraRepository.findById(idSeguradora)
            .orElseThrow(() -> new RuntimeException("Seguradora não encontrada: " + idSeguradora));
    }
}