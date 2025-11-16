package com.consultorhub.backend.repository;

import java.util.UUID;
import java.util.Optional;

import com.consultorhub.backend.model.Consultor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ConsultorRepository extends JpaRepository<Consultor, UUID> {
	
	Optional<Consultor> findByNome(String nome);
	
	Optional<Consultor> findByCpf(String cpf);
	
	Optional<Consultor> findByEmail(String email);
	
	Optional<Consultor> findByResetToken(String resetToken);
	
}
