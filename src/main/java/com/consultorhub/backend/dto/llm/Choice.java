package com.consultorhub.backend.dto.llm;

public class Choice {

	private ResponseMessage message;

	public Choice() {
		super();
	}

	public Choice(ResponseMessage message) {
		super();
		this.message = message;
	}

	public ResponseMessage getMessage() {
		return message;
	}

	public void setMessage(ResponseMessage message) {
		this.message = message;
	}
	
}
