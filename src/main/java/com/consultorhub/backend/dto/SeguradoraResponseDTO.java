package com.consultorhub.backend.dto;

import com.consultorhub.backend.model.Seguradora;
import java.util.UUID;

public class SeguradoraResponseDTO {
    private UUID id;
    private String nome;
    private String email;
    private String cnpj;
    private String status;

    public SeguradoraResponseDTO(Seguradora seguradora) {
        this.id = seguradora.getId();
        this.nome = seguradora.getNome();
        this.email = seguradora.getEmail();
        this.cnpj = seguradora.getCnpj();
        this.status = seguradora.getStatus();
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

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
    
    
    
}