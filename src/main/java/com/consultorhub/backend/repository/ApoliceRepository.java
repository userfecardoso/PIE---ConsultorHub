package com.consultorhub.backend.repository;

import java.util.UUID;
import com.consultorhub.backend.model.Apolice;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ApoliceRepository extends JpaRepository<Apolice, UUID> {
		
	List<Apolice> findByStatus(String status);
	
	List<Apolice> findByClienteId(UUID idCliente);

	List<Apolice> findByConsultorId(UUID idConsultor);

	List<Apolice> findBySeguradoraId(UUID idSeguradora);

	List<Apolice> findByStatusAndDataTerminoVigenciaBetween(String status, LocalDate dataInicio, LocalDate dataFim);
	// Essa mistura bizarra de ingles e portugues só ta assim pq o JpaRepository cria as queries
	// automaticamente usando o "findBy[NomeDoAtributo]".
}
