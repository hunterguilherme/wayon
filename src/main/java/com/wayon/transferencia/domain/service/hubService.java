package com.wayon.transferencia.domain.service;

import com.wayon.transferencia.domain.model.dto.TransferenciaDTO;

import java.util.List;

public interface hubService {
    TransferenciaDTO agendaTransferencia(TransferenciaDTO transferenciaDTO);


    List<TransferenciaDTO> getBankStatment(String conta);
}
