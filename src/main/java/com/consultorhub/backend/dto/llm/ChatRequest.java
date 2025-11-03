package com.consultorhub.backend.dto.llm;

import java.util.List;

public class ChatRequest {

	private String model;
	private List<ChatMessage> messages;
	private ResponseFormat response_format;
	
	
	public ChatRequest(String model, String userPrompt) {
        this.model = model;
        this.messages = List.of(new ChatMessage("user", userPrompt));
        this.response_format = new ResponseFormat("json_object");
    }
	
	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}



	public List<ChatMessage> getMessages() {
		return messages;
	}



	public void setMessages(List<ChatMessage> messages) {
		this.messages = messages;
	}



	public ResponseFormat getResponse_format() {
		return response_format;
	}



	public void setResponse_format(ResponseFormat response_format) {
		this.response_format = response_format;
	}

}
