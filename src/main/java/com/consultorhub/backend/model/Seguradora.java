package com.consultorhub.backend.model;

import java.util.List;
import java.util.UUID;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
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
	
	@OneToMany
	@JoinColumn(name = "seguradora")
	private List<Apolice> apolices;
	
	@ManyToMany
	@JoinTable(
			name="seguradora_consultor",
			joinColumns=@JoinColumn(name="seguradora_id"),
			inverseJoinColumns=@JoinColumn(name="consultor_id"))
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
	
}
