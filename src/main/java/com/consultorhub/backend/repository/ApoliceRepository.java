package com.consultorhub.backend.repository;

import com.consultorhub.backend.model.Apolice;
import com.consultorhub.backend.model.Cliente;
import com.consultorhub.backend.model.Consultor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface ApoliceRepository extends JpaRepository<Apolice, UUID> {
		
	List<Apolice> findByStatus(String status);
	
	List<Apolice> findByClienteId(UUID idCliente);

	List<Apolice> findByConsultorId(UUID idConsultor);

	List<Apolice> findBySeguradoraId(UUID idSeguradora);

	// 	/doc_reports
	List<Apolice> findByConsultor(Consultor consultor);
	
	List<Apolice> findByNumeroApolice(String numeroApolice);
	
	//  /client_docs
	List<Apolice> findByCliente(Cliente cliente);
	
	// Essa mistura bizarra de ingles e portugues só ta assim pq o JpaRepository cria as queries
	// automaticamente usando o "findBy[NomeDoAtributo]".
	List<Apolice> findByStatusAndDataTerminoVigenciaBetweenAndConsultor(
	        String status, 
	        LocalDate dataInicio, 
	        LocalDate dataFim, 
	        Consultor consultor
	);
	
	@Query("SELECT a FROM Apolice a WHERE a.consultor = :consultor AND a.tipo = :tipo AND a.status = 'ATIVO'")
    List<Apolice> findByConsultorAndTipoAtivas(
        @Param("consultor") Consultor consultor, 
        @Param("tipo") String tipo
    );
	
}
