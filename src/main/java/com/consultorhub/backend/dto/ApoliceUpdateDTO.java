package com.consultorhub.backend.dto;

import java.time.LocalDate;

public class ApoliceUpdateDTO {
    private Double valorPremio;
    private LocalDate dataInicioVigencia;
    private LocalDate dataTerminoVigencia;
    private String status;
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