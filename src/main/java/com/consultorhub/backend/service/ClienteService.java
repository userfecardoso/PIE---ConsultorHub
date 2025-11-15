package com.consultorhub.backend.service;

import com.consultorhub.backend.repository.ClienteRepository;
import com.consultorhub.backend.repository.ApoliceRepository;
import com.consultorhub.backend.repository.SeguradoraRepository;
import com.consultorhub.backend.model.Cliente;
import com.consultorhub.backend.model.Apolice;
import com.consultorhub.backend.model.Consultor;
import com.consultorhub.backend.dto.ClienteDTO;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ClienteService {

	private final ClienteRepository clienteRepository;
	private final ApoliceRepository apoliceRepository;
	private final SeguradoraRepository seguradoraRepository;
	
	public ClienteService(ClienteRepository clienteRepository, ApoliceRepository apoliceRepository, SeguradoraRepository seguradoraRepository) {
		this.clienteRepository = clienteRepository;
		this.apoliceRepository = apoliceRepository;
		this.seguradoraRepository = seguradoraRepository;
	}
	
	public Cliente criarCliente(ClienteDTO dto, Consultor consultorLogado) {
        if (clienteRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Email já cadastrado.");
        }
        
        Cliente novoCliente = new Cliente();
        
        novoCliente.setNome(dto.getNome());
        novoCliente.setEmail(dto.getEmail());
        novoCliente.setCpf(dto.getCpf());
        novoCliente.setTelefone(dto.getTelefone());
        novoCliente.setNotas(dto.getNotas());
        
        novoCliente.setConsultor(consultorLogado); 
        
        return clienteRepository.save(novoCliente);
    }
	
// --------------------------------------------------------------------------
	
	//   /client_reports
	public List<Cliente> ClientesPorConsultor(Consultor consultorLogado){
		return clienteRepository.findByConsultorAndStatus(consultorLogado, "ATIVO");
	}
	
// --------------------------------------------------------------------------
	
	//	 /client_search
	public Cliente obterClientePorId(UUID idCliente, Consultor consultorLogado) {
        Cliente cliente = clienteRepository.findById(idCliente)
            .orElseThrow(() -> new RuntimeException("O ID inserido não é válido."));
        
        validarPropriedadeCliente(cliente, consultorLogado);
        
        return cliente;
    }
	
	private void validarPropriedadeCliente(Cliente cliente, Consultor consultorLogado) {
        if (!cliente.getConsultor().getId().equals(consultorLogado.getId())) {
            throw new AccessDeniedException("Acesso Negado: Este cliente não está associado a você.");
        }
    }
	
// --------------------------------------------------------------------------
	
	//   /client_update
	public Cliente atualizarCliente(UUID idCliente, ClienteDTO dto, Consultor consultorLogado) {

        Cliente cliente = obterClientePorId(idCliente, consultorLogado);

        if (dto.getNome() != null) {
            cliente.setNome(dto.getNome());
        }
        if (dto.getEmail() != null) {
            cliente.setEmail(dto.getEmail());
        }
        if (dto.getCpf() != null) {
            cliente.setCpf(dto.getCpf());
        }
        if (dto.getTelefone() != null) {
            cliente.setTelefone(dto.getTelefone());
        }
        if (dto.getNotas() != null) {
            cliente.setNotas(dto.getNotas());
        }
        
        return clienteRepository.save(cliente);
    }
	
// --------------------------------------------------------------------------
	
	//	 /client_delete
	public void deletarCliente(UUID idCliente, Consultor consultorLogado) {

        Cliente cliente = obterClientePorId(idCliente, consultorLogado);
        cliente.setStatus("INATIVO");
        
        clienteRepository.save(cliente);
    }
	
// --------------------------------------------------------------------------
	
	//	 /client_docs
	public List<Apolice> apolicesCliente(UUID idCliente, Consultor consultorLogado) {

        Cliente cliente = obterClientePorId(idCliente, consultorLogado);
        
        return apoliceRepository.findByCliente(cliente);
    }
	
// --------------------------------------------------------------------------
	
	//	 /client_company_reports
	public List<Cliente> obterClientesPorSeguradora(UUID idSeguradora, Consultor consultorLogado) {
        
		UUID consultorId = consultorLogado.getId();
		boolean associado = seguradoraRepository.existsByIdAndConsultoresId(idSeguradora, consultorId);
		
		if(!associado) {
			throw new AccessDeniedException("Erro: Consultor não associado à seguradora");
		}
		
        return clienteRepository.findByConsultorAndSeguradoraId(consultorLogado, idSeguradora);
    }
	
}
