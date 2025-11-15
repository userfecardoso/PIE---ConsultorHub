package com.consultorhub.backend.service;

import com.consultorhub.backend.repository.ApoliceRepository;
import com.consultorhub.backend.repository.ClienteRepository;
import com.consultorhub.backend.repository.SeguradoraRepository;

import com.consultorhub.backend.model.Apolice;
import com.consultorhub.backend.model.Cliente;
import com.consultorhub.backend.model.Consultor;
import com.consultorhub.backend.model.Seguradora;

import com.consultorhub.backend.dto.ApoliceUpdateDTO;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.security.access.AccessDeniedException;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ApoliceService {

	private final ApoliceRepository apoliceRepository;
	private final SeguradoraRepository seguradoraRepository;
	private final ClienteRepository clienteRepository;
	private final PDFBoxService pdfService;
	private final LlmService llmService;
	private final ObjectMapper objectMapper;
	private final FileStorageService fileStorageService;

	
	public ApoliceService(ApoliceRepository apoliceRepository, 
			PDFBoxService pdfService, 
			LlmService llmService, 
			ClienteRepository clienteRepository,
			SeguradoraRepository seguradoraRepository,
			ObjectMapper objectMapper,
			FileStorageService fileStorageService) {
		this.apoliceRepository = apoliceRepository;
		this.pdfService = pdfService;
		this.llmService = llmService;
		this.clienteRepository = clienteRepository;
		this.seguradoraRepository = seguradoraRepository;
		this.objectMapper = objectMapper;
		this.fileStorageService = fileStorageService;

	}
	
	public Apolice uploadDocument(MultipartFile file, Consultor consultorLogado, String idClienteString, String idSeguradoraString, String notas) throws Exception{
		
		String fileName = fileStorageService.storeFile(file);
		
		String extractedText;
		
		System.out.println("Entrou na função");
		
		try(InputStream inputStream = file.getInputStream()){
			extractedText = pdfService.extractText(inputStream);
		}
		
		System.out.printf("Texto: %s", extractedText);
		
		System.out.println("Terminou de extrair o texto do pdf");

		String documentJson = llmService.extractData(extractedText);
		
		System.out.println("Passou pela LLM");
		
		System.out.printf("Texto: %s", documentJson);


		
		Apolice newApolice = objectMapper.readValue(documentJson, Apolice.class);
		
		System.out.println("Criou a nova apólice");
		
		newApolice.setStatus("ATIVO");
		
		newApolice.setNotas(notas);
		
		System.out.println("Setou status");
		UUID idCliente = UUID.fromString(idClienteString);
		

		Cliente cliente = clienteRepository.findById(idCliente)
				.orElseThrow(() -> new RuntimeException("Nenhum cliente existente com o id inserido.")); 
		
		newApolice.setCliente(cliente);
		
		System.out.println("Setou cliente");
		
		UUID idSeguradora = UUID.fromString(idSeguradoraString);
		
		Seguradora seguradora = seguradoraRepository.findById(idSeguradora)
				.orElseThrow(() -> new RuntimeException("Nenhuma seguradora existente com o id inserido."));
		
		System.out.println("Setou seguradora");
		
		boolean consultorTemSeguradora = seguradoraRepository.existsByIdAndConsultoresId(
			seguradora.getId(), 
			consultorLogado.getId()
		);
		
		if (!consultorTemSeguradora) {
			throw new AccessDeniedException("Erro: O consultor não tem associação com a seguradora inserida");
		}
		
		System.out.println("Consultor está associado com a seguradora inserida.");

		newApolice.setSeguradora(seguradora);
		
		System.out.println("Setou seguradora");
		
		newApolice.setConsultor(consultorLogado);

		newApolice.setNomeArquivo(fileName);
		
		return apoliceRepository.save(newApolice);
	}
	
	
	//   /near_end
	public List<Apolice> obterApolicesComVencimentoProximo(Consultor consultorLogado) {
        LocalDate hoje = LocalDate.now();
        LocalDate dataLimite = hoje.plusDays(30);
        
        return apoliceRepository.findByStatusAndDataTerminoVigenciaBetweenAndConsultor(
            "ATIVA", 
            hoje, 
            dataLimite, 
            consultorLogado
        );
    }
	
	//  /doc_reports
	public List<Apolice> obterApolicesPorConsultor(Consultor consultorLogado) {
        return apoliceRepository.findByConsultor(consultorLogado);
    }
	
	
	//  /doc_update
	public Apolice atualizarApolice(UUID apoliceId, ApoliceUpdateDTO dto, Consultor consultorLogado) {
        Apolice apolice = apoliceRepository.findById(apoliceId)
            .orElseThrow(() -> new RuntimeException("Apólice não encontrada com ID: " + apoliceId));

        if (!apolice.getConsultor().getId().equals(consultorLogado.getId())) {
            throw new AccessDeniedException("Acesso Negado: Esta apólice não pertence a você.");
        }

        if (dto.getValorPremio() != null) {
            apolice.setValorPremio(dto.getValorPremio());
        }
        if (dto.getDataInicioVigencia() != null) {
            apolice.setDataInicioVigencia(dto.getDataInicioVigencia());
        }
        if (dto.getDataTerminoVigencia() != null) {
            apolice.setDataTerminoVigencia(dto.getDataTerminoVigencia());
        }
        if (dto.getStatus() != null) {
            apolice.setStatus(dto.getStatus());
        }

        return apoliceRepository.save(apolice);
    }
	
	//  /doc_delete
	public Apolice desativarApolice(UUID apoliceId, Consultor consultorLogado) {
        Apolice apolice = apoliceRepository.findById(apoliceId)
            .orElseThrow(() -> new RuntimeException("Não existem apólices com o ID inserido."));

        if (!apolice.getConsultor().getId().equals(consultorLogado.getId())) {
            throw new AccessDeniedException("Acesso Negado: Esta apólice não está associada à sua conta.");
        }
        
        apolice.setStatus("INATIVO"); 
        
        return apoliceRepository.save(apolice);
    }
	
	
	//   /cliet_docs
	public List<Apolice> obterApolicesPorCliente(UUID idCliente, Consultor consultorLogado) {
        Cliente cliente = clienteRepository.findById(idCliente)
            .orElseThrow(() -> new RuntimeException("Não existe um cliente com o ID inserido."));

        if (!cliente.getConsultor().getId().equals(consultorLogado.getId())) {
            throw new AccessDeniedException("Acesso Negado: Este cliente não está associado à sua conta..");
        }
        
        return apoliceRepository.findByCliente(cliente);
    }
	
	public List<Apolice> obterApolicesPorTipo(Consultor consultorLogado, String tipo) {
        
        return apoliceRepository.findByConsultorAndTipoAtivas(
            consultorLogado, 
            tipo
        );
    }
	
}
