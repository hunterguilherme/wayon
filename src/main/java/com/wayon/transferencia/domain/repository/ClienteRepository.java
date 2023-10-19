package com.wayon.transferencia.domain.repository;


import com.wayon.transferencia.domain.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    boolean existsByConta(String conta);
}
