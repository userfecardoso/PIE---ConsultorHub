package com.consultorhub.backend.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterRequestDTO {

	@NotBlank(message = "O nome não pode estar em branco")
    private String nome;
	
	@NotBlank(message = "O CPF não pode estar em branco")
    private String cpf;
	
	@NotBlank(message = "O email não pode estar em branco")
	@Email(message = "O formato do email é inválido")
    private String email;
	
	@NotBlank(message = "A senha não pode estar em branco")
	@Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres")
    private String senha;
    
    
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
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}

}