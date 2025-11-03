package com.consultorhub.backend.service;

import com.consultorhub.backend.repository.ApoliceRepository;
import com.consultorhub.backend.repository.ClienteRepository;
import com.consultorhub.backend.repository.ConsultorRepository;
import com.consultorhub.backend.repository.SeguradoraRepository;

import com.consultorhub.backend.model.Apolice;
import com.consultorhub.backend.model.Cliente;
import com.consultorhub.backend.model.Consultor;
import com.consultorhub.backend.model.Seguradora;

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

	
	public ApoliceService(ApoliceRepository apoliceRepository, 
			PDFBoxService pdfService, 
			LlmService llmService, 
			ClienteRepository clienteRepository,
			SeguradoraRepository seguradoraRepository,
			ObjectMapper objectMapper) {
		this.apoliceRepository = apoliceRepository;
		this.pdfService = pdfService;
		this.llmService = llmService;
		this.clienteRepository = clienteRepository;
		this.seguradoraRepository = seguradoraRepository;
		this.objectMapper = objectMapper;

	}
	
	public Apolice uploadDocument(MultipartFile file, Consultor consultorLogado, String idClienteString, String idSeguradoraString) throws Exception{
		String extractedText;
		
		try(InputStream inputStream = file.getInputStream()){
			extractedText = pdfService.extractText(inputStream);
		}

		String documentJson = llmService.extractData(extractedText);
		
		Apolice newApolice = objectMapper.readValue(documentJson, Apolice.class);
		
		newApolice.setStatus("ATIVO");
		UUID idCliente = UUID.fromString(idClienteString);
		Cliente cliente = clienteRepository.findById(idCliente)
				.orElseThrow(() -> new RuntimeException("Nenhum cliente existente com o id inserido.")); 
		
		newApolice.setCliente(cliente);
		
		UUID idSeguradora = UUID.fromString(idSeguradoraString);
		
		Seguradora seguradora = seguradoraRepository.findById(idSeguradora)
				.orElseThrow(() -> new RuntimeException("Nenhuma seguradora existente com o id inserido."));
		
		boolean consultorTemSeguradora = consultorLogado.getSeguradoras().stream()
				.anyMatch(segu -> segu.getId().equals(seguradora.getId()));
		
		if (!consultorTemSeguradora) {
			throw new AccessDeniedException("Erro: O consultor não tem associação com a seguradora inserida");
		}
		
		newApolice.setSeguradora(seguradora);
		
		return apoliceRepository.save(newApolice);
	}
	
	
	public List<Apolice> ObterApolicesComVencimentoProximo(){
		LocalDate hoje = LocalDate.now();
		LocalDate dataTermino = hoje.plusDays(30);
		String status = "ATIVO";
		// Na documentação decidimos colocar 30 dias, se quiser aumentar ou diminuir é
		// só trocar o valor aqui.
		
		return apoliceRepository.findByStatusAndDataTerminoVigenciaBetween(status, hoje, dataTermino);
	}
	
	
}
