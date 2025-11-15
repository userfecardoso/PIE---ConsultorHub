package com.consultorhub.backend.model;


import java.util.UUID;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Id;
import org.hibernate.annotations.CreationTimestamp;

@Entity
public class Apolice{

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@ManyToOne
	@JoinColumn(name="idCliente", nullable=false)
	private Cliente cliente;
	
	@ManyToOne
	@JoinColumn(name="idConsultor", nullable=false)
	private Consultor consultor;
	
	@ManyToOne
	@JoinColumn(name="idSeguradora", nullable=false)
	private Seguradora seguradora;
	
	@CreationTimestamp
	private LocalDateTime createdOn;
	
	@Column(columnDefinition = "TEXT")
    private String notas;
	
	private Double valorPremio;
	private LocalDate dataInicioVigencia;
	private LocalDate dataTerminoVigencia;
	private String status;
	private String tipo;
	private String nomeArquivo;
	
	public Apolice() {
	
	}

	public Apolice(UUID id, Cliente cliente, Consultor consultor, Seguradora seguradora, LocalDateTime createdOn,
			String notas, Double valorPremio, LocalDate dataInicioVigencia, LocalDate dataTerminoVigencia,
			String status, String tipo) {
		super();
		this.id = id;
		this.cliente = cliente;
		this.consultor = consultor;
		this.seguradora = seguradora;
		this.createdOn = createdOn;
		this.notas = notas;
		this.valorPremio = valorPremio;
		this.dataInicioVigencia = dataInicioVigencia;
		this.dataTerminoVigencia = dataTerminoVigencia;
		this.status = status;
		this.tipo = tipo;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Consultor getConsultor() {
		return consultor;
	}

	public void setConsultor(Consultor consultor) {
		this.consultor = consultor;
	}

	public Seguradora getSeguradora() {
		return seguradora;
	}

	public void setSeguradora(Seguradora seguradora) {
		this.seguradora = seguradora;
	}

	public Double getValorPremio() {
		return valorPremio;
	}

	public void setValorPremio(Double valorPremio) {
		this.valorPremio = valorPremio;
	}

	public LocalDate getDataInicioVigencia() {
		return dataInicioVigencia;
	}

	public void setDataInicioVigencia(LocalDate dataInicioVigencia) {
		this.dataInicioVigencia = dataInicioVigencia;
	}

	public LocalDate getDataTerminoVigencia() {
		return dataTerminoVigencia;
	}

	public void setDataTerminoVigencia(LocalDate dataTerminoVigencia) {
		this.dataTerminoVigencia = dataTerminoVigencia;
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getNotas() {
		return notas;
	}

	public void setNotas(String notas) {
		this.notas = notas;
	}

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}
	
}
