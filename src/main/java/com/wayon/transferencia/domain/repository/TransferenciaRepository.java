package com.wayon.transferencia.domain.repository;

import com.wayon.transferencia.domain.model.FinancialTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransferenciaRepository extends JpaRepository<FinancialTransfer, Long> {

    List<FinancialTransfer> findByContaOrigem(String conta);
}
