package com.consultorhub.backend.repository;

import java.util.UUID;

import com.consultorhub.backend.model.Consultor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


@Repository
public interface ConsultorRepository extends JpaRepository<Consultor, UUID> {
	
	Optional<Consultor> findByNome(String nome);
	
	Optional<Consultor> findByCpf(String cpf);
	
	Optional<Consultor> findByEmail(String email);
	
	
}
