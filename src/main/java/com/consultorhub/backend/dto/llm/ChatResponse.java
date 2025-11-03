package com.consultorhub.backend.dto.llm;

import java.util.List;

public class ChatResponse {

	private List<Choice> choices;

	public ChatResponse() {
		super();
	}

	public ChatResponse(List<Choice> choices) {
		super();
		this.choices = choices;
	}

	public List<Choice> getChoices() {
		return choices;
	}

	public void setChoices(List<Choice> choices) {
		this.choices = choices;
	}
	
	
	
}
