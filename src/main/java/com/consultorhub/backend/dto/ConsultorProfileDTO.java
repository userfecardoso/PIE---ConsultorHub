package com.consultorhub.backend.dto;
import com.consultorhub.backend.model.Consultor;

import java.util.UUID;

public class ConsultorProfileDTO {
    private UUID id;
    private String nome;
    private String email;
    
    public ConsultorProfileDTO() {
		super();
	}

	public UUID getId() {
		return id;
	}


	public void setId(UUID id) {
		this.id = id;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


    public ConsultorProfileDTO(Consultor consultor) {
        this.id = consultor.getId();
        this.nome = consultor.getNome();
        this.email = consultor.getEmail();
    }
}