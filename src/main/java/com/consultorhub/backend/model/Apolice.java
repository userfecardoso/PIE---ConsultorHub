package com.consultorhub.backend.model;


import java.util.UUID;
import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Id;

@Entity
public class Apolice{

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@ManyToOne
	@JoinColumn(name="id_cliente", nullable=false)
	private Cliente cliente;
	
	@ManyToOne
	@JoinColumn(name="id_consultor", nullable=false)
	private Consultor consultor;
	
	@ManyToOne
	@JoinColumn(name="id_seguradora", nullable=false)
	private Seguradora seguradora;
	
	
	private Double valorPremio;
	private LocalDate dataInicioVigencia;
	private LocalDate dataTerminoVigencia;
	private String status;
	
	public Apolice() {
	
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
	
}
