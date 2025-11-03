package com.consultorhub.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientWebConfig {

	@Value("${llm.api.baseurl}")
	private String llmUrl;
	
	@Bean
	public WebClient llmWebClient(WebClient.Builder builder) {
		return builder
				.baseUrl(llmUrl)
				.build();
	}
	
}
