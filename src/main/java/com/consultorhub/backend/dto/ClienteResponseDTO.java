package com.consultorhub.backend.dto;

import com.consultorhub.backend.model.Cliente;
import java.util.UUID;
import java.time.LocalDateTime;

public class ClienteResponseDTO {

    private UUID id;
    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private String status;
    private String notas;

	private LocalDateTime createdOn;

    public ClienteResponseDTO(Cliente cliente) {
        this.id = cliente.getId();
        this.nome = cliente.getNome();
        this.cpf = cliente.getCpf();
        this.email = cliente.getEmail();
        this.telefone = cliente.getTelefone();
        this.status = cliente.getStatus();
        this.notas = cliente.getNotas();
		this.createdOn = cliente.getCreatedOn(); // Mapeando os novos campos para o DTO
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

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNotas() {
		return notas;
	}

	public void setNotas(String notas) {
		this.notas = notas;
	}

	// Adicionado os Getters e Setters do createdOn
	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}
 
}