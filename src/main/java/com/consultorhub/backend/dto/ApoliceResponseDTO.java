package com.consultorhub.backend.dto;

import com.consultorhub.backend.model.Apolice;
import java.time.LocalDate;
import java.util.UUID;

public class ApoliceResponseDTO {
    private UUID id;
    private Double valorPremio;
    private LocalDate dataInicioVigencia;
    private LocalDate dataTerminoVigencia;
    private String status;
    private String notas;
    private String numeroApolice;
    private UUID clienteId;
    private UUID consultorId;
    private UUID seguradoraId;

    public ApoliceResponseDTO(Apolice apolice) {
        this.id = apolice.getId();
        this.valorPremio = apolice.getValorPremio();
        this.dataInicioVigencia = apolice.getDataInicioVigencia();
        this.dataTerminoVigencia = apolice.getDataTerminoVigencia();
        this.status = apolice.getStatus();
        this.clienteId = apolice.getCliente().getId();
        this.consultorId = apolice.getConsultor().getId();
        this.seguradoraId = apolice.getSeguradora().getId();
        this.notas = apolice.getNotas();
        this.numeroApolice = apolice.getNumeroApolice();
    }

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
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

	public UUID getClienteId() {
		return clienteId;
	}

	public void setClienteId(UUID clienteId) {
		this.clienteId = clienteId;
	}

	public UUID getConsultorId() {
		return consultorId;
	}

	public void setConsultorId(UUID consultorId) {
		this.consultorId = consultorId;
	}

	public UUID getSeguradoraId() {
		return seguradoraId;
	}

	public void setSeguradoraId(UUID seguradoraId) {
		this.seguradoraId = seguradoraId;
	}

	public String getNotas() {
		return notas;
	}

	public void setNotas(String notas) {
		this.notas = notas;
	}

	public String getNumeroApolice() {
		return numeroApolice;
	}

	public void setNumeroApolice(String numeroApolice) {
		this.numeroApolice = numeroApolice;
	}
	
}