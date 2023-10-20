package com.wayon.transferencia.domain.service;

import com.wayon.transferencia.domain.model.dto.FinancialTransfereDTO;

import java.util.List;

public interface HubService {
    FinancialTransfereDTO scheduleFinancialTransfer(FinancialTransfereDTO financialTransfereDTO);


    List<FinancialTransfereDTO> getBankStatment(String conta);
}
