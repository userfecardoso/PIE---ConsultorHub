// Em: service/ConsultorService.java
package com.consultorhub.backend.service;

import com.consultorhub.backend.dto.ConsultorUpdateDTO;
import com.consultorhub.backend.model.Consultor;
import com.consultorhub.backend.repository.ConsultorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConsultorService {

    private final ConsultorRepository consultorRepository;

    public ConsultorService(ConsultorRepository consultorRepository) {
        this.consultorRepository = consultorRepository;
    }

    @Transactional
    public Consultor atualizarPerfil(Consultor consultorLogado, ConsultorUpdateDTO dto) {
        
        Consultor consultorParaAtualizar = consultorRepository.findById(consultorLogado.getId())
            .orElseThrow(() -> new RuntimeException("Consultor não encontrado na sessão (isso não deveria acontecer)"));

        if (dto.getNome() != null && !dto.getNome().isEmpty()) {
            consultorParaAtualizar.setNome(dto.getNome());
        }

        return consultorRepository.save(consultorParaAtualizar);
    }
}