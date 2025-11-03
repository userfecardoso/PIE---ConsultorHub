package com.consultorhub.backend.repository;

import java.util.UUID;
import com.consultorhub.backend.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, UUID>{
		
	Optional<Cliente> findByNome(String nome);
	
	Optional<Cliente> findByCpf(String cpf);
	
	Optional<Cliente> findByEmail(String email);
	
	Optional<Cliente> findByTelefone(String telefone);

}
