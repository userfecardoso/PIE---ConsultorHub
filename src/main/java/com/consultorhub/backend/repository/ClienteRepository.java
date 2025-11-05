package com.consultorhub.backend.repository;

import com.consultorhub.backend.model.Cliente;
import com.consultorhub.backend.model.Consultor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, UUID>{
		
	Optional<Cliente> findByNome(String nome);
	
	Optional<Cliente> findByCpf(String cpf);
	
	Optional<Cliente> findByEmail(String email);
	
	Optional<Cliente> findByTelefone(String telefone);

	//   /client_reports
	List<Cliente> findByConsultorAndStatus(Consultor consultor, String status);
	
	//   /client_company_reports
	@Query("SELECT DISTINCT c FROM Cliente c JOIN c.apolices a WHERE c.consultor = :consultor AND a.seguradora.id = :seguradoraId AND c.status = 'ATIVO'")
	List<Cliente> findByConsultorAndSeguradoraId(
		@Param("consultor") Consultor consultor,
		@Param("seguradoraId") UUID seguradoraId
	);	
}
