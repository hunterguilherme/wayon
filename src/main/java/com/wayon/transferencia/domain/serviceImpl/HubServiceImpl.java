package com.wayon.transferencia.domain.serviceImpl;

import com.wayon.transferencia.domain.exceptions.EntityNotFoundException;
import com.wayon.transferencia.domain.exceptions.WrongTaxException;
import com.wayon.transferencia.domain.model.FinancialTransfer;
import com.wayon.transferencia.domain.model.dto.FinancialTransfereDTO;
import com.wayon.transferencia.domain.repository.ClienteRepository;
import com.wayon.transferencia.domain.repository.TransferenciaRepository;
import com.wayon.transferencia.domain.service.HubService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.round;

@Service
public class HubServiceImpl implements HubService {

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private TransferenciaRepository transferenciaRepository;

    @Override
    public FinancialTransfereDTO scheduleFinancialTransfer(FinancialTransfereDTO financialTransfereDTO) {
        ModelMapper mapper = new ModelMapper();
        FinancialTransfer financialTransfer = new FinancialTransfer();

        clientValidation(financialTransfereDTO);
        mapper.map(financialTransfereDTO, financialTransfer);
        financialTransfer.setTaxa(taxValueCalculation(financialTransfereDTO));
        financialTransfereDTO.setTaxa(taxValueCalculation(financialTransfereDTO));

        transferenciaRepository.save(financialTransfer);
        return financialTransfereDTO;
    }

    private BigDecimal taxValueCalculation(FinancialTransfereDTO transferencia) {
        BigDecimal conversaoPorcentagem = BigDecimal.valueOf(getTaxPercentage(transferencia));
        return transferencia.getValor().multiply(conversaoPorcentagem).divide(BigDecimal.valueOf(100));
    }

    private static Double getTaxPercentage(FinancialTransfereDTO transferencia) {
        Double[] tabelaTaxa = {2.5d, 0d, 8.2d, 6.9d, 4.7d, 1.7d};
        LocalDate dataAgendamento = transferencia.getDataAgendamento();
        LocalDate dataTransferencia = transferencia.getDataTransferencia();
        Long dias = Duration.between(dataAgendamento.atStartOfDay(), dataTransferencia.atStartOfDay()).toDays();
        int faxaTaxa = (dias % 10 == 0) ? dias.intValue() / 10 : round(dias / 10) + 1;
        if (tabelaTaxa[faxaTaxa] == 0) {
            throw new WrongTaxException("Não pode haver taxa zerada.");
        } else {
            return tabelaTaxa[faxaTaxa];
        }

    }

    private void clientValidation(FinancialTransfereDTO transferencia) {

        boolean clienteOrigem = clienteRepository.existsByConta(transferencia.getContaOrigem());
        boolean clienteDestino = clienteRepository.existsByConta(transferencia.getContaDestino());
        if (!(clienteOrigem && clienteDestino))
            throw new EntityNotFoundException("Conta de origem ou de destino não existente. Verificar digitos");
    }

    @Override
    public List<FinancialTransfereDTO> getBankStatment(String conta) {
        List<FinancialTransfer> financialTransfers = transferenciaRepository.findByContaOrigem(conta);

        List<FinancialTransfereDTO> transferenciasDTO = new ArrayList<>();
        ModelMapper mapper = new ModelMapper();

        financialTransfers.forEach(financialTransfer ->{
            FinancialTransfereDTO financialTransfereDTO = new FinancialTransfereDTO();
            mapper.map(financialTransfer, financialTransfereDTO);
            transferenciasDTO.add(financialTransfereDTO);
        });

        return transferenciasDTO;
    }


    public HubServiceImpl(ClienteRepository clienteRepository,TransferenciaRepository transferenciaRepository ) {
        this.clienteRepository = clienteRepository;
        this.transferenciaRepository = transferenciaRepository;
    }
}
