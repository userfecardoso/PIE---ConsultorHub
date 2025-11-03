package com.consultorhub.backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.consultorhub.backend.dto.llm.ChatRequest;
import com.consultorhub.backend.dto.llm.ChatResponse;

@Service
public class LlmService {
	private final WebClient llmWebClient;
	private final String apiKey;
	
	public LlmService(WebClient llmWebClient, @Value("${llm.api.key}") String apiKey) {
		this.llmWebClient = llmWebClient;
		this.apiKey = apiKey;
	}
	
	public String extractData(String documentText) {
		
		String prompt = """
	            Você é um assistente especialista em extração de dados de apólices de seguro.
	            Baseado no texto de OCR a seguir, extraia as informações e retorne **apenas** um objeto JSON válido.
	            O JSON deve ter EXATAMENTE a seguinte estrutura (use "null" para campos não encontrados):
	            {
	              "valorPremio": 0.0,
	              "dataInicioVigencia": "YYYY-MM-DD",
	              "dataTerminoVigencia": "YYYY-MM-DD",
	            }
	            
	            TEXTO DA APÓLICE:
	            ---
	            %s
	            ---
	            """.formatted(documentText);
		
		ChatRequest request = new ChatRequest("gpt-4-turbo", prompt);
		
		ChatResponse response = llmWebClient.post()
	            .uri("/chat/completions")
	            .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey) 
	            .contentType(MediaType.APPLICATION_JSON)
	            .bodyValue(request) 
	            .retrieve() 
	            .bodyToMono(ChatResponse.class)
	            .block();
		
		if (response != null && !response.getChoices().isEmpty()) {

            return response.getChoices().get(0).getMessage().getContent();
        } else {
            throw new RuntimeException("Falha ao extrair dados do LLM. Resposta vazia.");
        }
		
	}
	
}
