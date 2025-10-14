package com.consultorhub.backend.repository;

import java.util.UUID;
import com.consultorhub.backend.model.Seguradora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeguradoraRepository extends JpaRepository<Seguradora, UUID> {

}
