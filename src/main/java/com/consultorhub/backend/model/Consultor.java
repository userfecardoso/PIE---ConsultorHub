package com.consultorhub.backend.model;

import java.util.List;
import java.util.UUID;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Id;

@Entity
public class Consultor {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	private String nome;
	private String cpf;
	private String email;
	
	@OneToMany(mappedBy = "consultor")
	private List<Apolice> apolices;
	
	@OneToMany(mappedBy = "consultores")
	private List<Seguradora> seguradoras;
	
	@ManyToMany(mappedBy = "consultor")
	private List<Cliente> clientes;
	
	public Consultor() {
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

	public List<Apolice> getApolices() {
		return apolices;
	}

	public void setApolices(List<Apolice> apolices) {
		this.apolices = apolices;
	}

	public List<Seguradora> getSeguradoras() {
		return seguradoras;
	}

	public void setSeguradoras(List<Seguradora> seguradoras) {
		this.seguradoras = seguradoras;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}
	
}
