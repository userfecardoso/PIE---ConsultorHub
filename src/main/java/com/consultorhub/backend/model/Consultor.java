package com.consultorhub.backend.model;

import java.util.List;
import java.util.UUID;
import java.time.LocalDateTime;
import java.util.Collection;import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class Consultor implements UserDetails{

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@CreationTimestamp
	private LocalDateTime createdOn;
	
	private String nome;
	private String cpf;
	private String email;
	private String senha;
	
	@OneToMany(mappedBy = "consultor")
	private List<Apolice> apolices;
		
	@OneToMany(mappedBy = "consultor")
	private List<Cliente> clientes;
	
	@ManyToMany
	@JoinTable(
			name="consultor_seguradora",
			joinColumns= @JoinColumn(name="consultor_id"),
			inverseJoinColumns = @JoinColumn(name="seguradora_id")
	)
	private List<Seguradora> seguradoras;
	
	public Consultor() {
	}

	public Consultor(UUID id, LocalDateTime createdOn, String nome, String cpf, String email, String senha,
			List<Apolice> apolices, List<Cliente> clientes, List<Seguradora> seguradoras) {
		super();
		this.id = id;
		this.createdOn = createdOn;
		this.nome = nome;
		this.cpf = cpf;
		this.email = email;
		this.senha = senha;
		this.apolices = apolices;
		this.clientes = clientes;
		this.seguradoras = seguradoras;
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
	
//	~~~~~~~~~~~~~~~~~~~~~~~
	
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	@Override
	public String getPassword() {
		return this.senha;
	}
	
	@Override
	public String getUsername() {
		return this.email; // Preferi usar o email como "username", mas se quiser mudar dps...
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("ROLE_CONSULTOR"));
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true; 
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true; 
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}
	
}
