package com.consultorhub.backend.repository;

import java.util.UUID;
import com.consultorhub.backend.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, UUID>{

}
