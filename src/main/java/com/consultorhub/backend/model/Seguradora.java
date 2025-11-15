package com.consultorhub.backend.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.ManyToMany;


@Entity
public class Seguradora {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	private String nome;
	private String email;
	private String cnpj;
	private String status = "ATIVO";
	
	@CreationTimestamp
	private LocalDateTime createdOn;
	
	@OneToMany(mappedBy="seguradora")
	private List<Apolice> apolices;
	
	@ManyToMany(mappedBy="seguradoras")
	private List<Consultor> consultores;

	public Seguradora() {
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

	public List<Apolice> getApolices() {
		return apolices;
	}

	public void setApolices(List<Apolice> apolices) {
		this.apolices = apolices;
	}

	public List<Consultor> getConsultores() {
		return consultores;
	}

	public void setConsultores(List<Consultor> consultores) {
		this.consultores = consultores;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}
	
}
