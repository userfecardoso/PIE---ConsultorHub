package com.consultorhub.backend.dto.llm;

public class ResponseMessage {

	public String content;

	public ResponseMessage() {
		super();
	}

	public ResponseMessage(String content) {
		super();
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
