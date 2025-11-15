package com.consultorhub.backend.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@CreationTimestamp
	private LocalDateTime createdOn;
	
	@Column(columnDefinition = "TEXT")
	private String notas;
	
	private String nome;
	private String cpf;
	private String email;
	private String telefone;
	private String status = "ATIVO";
	
	@OneToMany(mappedBy = "cliente", orphanRemoval = true)
	private List<Apolice> apolices;
	
	@ManyToOne
	@JoinColumn(name="consultor_id")
	private Consultor consultor;
	
	public Cliente() {
	}

	public Cliente(UUID id, LocalDateTime createdOn, String notas, String nome, String cpf, String email,
			String telefone, String status, List<Apolice> apolices, Consultor consultor) {
		super();
		this.id = id;
		this.createdOn = createdOn;
		this.notas = notas;
		this.nome = nome;
		this.cpf = cpf;
		this.email = email;
		this.telefone = telefone;
		this.status = status;
		this.apolices = apolices;
		this.consultor = consultor;
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


	public List<Apolice> getApolices() {
		return apolices;
	}


	public void setApolices(List<Apolice> apolices) {
		this.apolices = apolices;
	}


	public Consultor getConsultor() {
		return consultor;
	}


	public void setConsultor(Consultor consultor) {
		this.consultor = consultor;
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

	public String getNotas() {
		return notas;
	}

	public void setNotas(String notas) {
		this.notas = notas;
	}
	
}

