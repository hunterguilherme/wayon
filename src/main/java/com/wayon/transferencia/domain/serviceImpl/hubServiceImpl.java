package com.wayon.transferencia.domain.serviceImpl;

import com.wayon.transferencia.domain.exceptions.PreenchimentoIncorretoException;
import com.wayon.transferencia.domain.model.Transferencia;
import com.wayon.transferencia.domain.model.dto.TransferenciaDTO;
import com.wayon.transferencia.domain.repository.ClienteRepository;
import com.wayon.transferencia.domain.repository.TransferenciaRepository;
import com.wayon.transferencia.domain.service.hubService;
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
public class hubServiceImpl implements hubService {

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private TransferenciaRepository transferenciaRepository;

    @Override
    public TransferenciaDTO agendaTransferencia(TransferenciaDTO transferenciaDTO) {
        ModelMapper mapper = new ModelMapper();
        Transferencia transferencia = new Transferencia();

        validaCliente(transferenciaDTO);
        mapper.map(transferenciaDTO, transferencia);
        transferencia.setTaxa(calculaValorTaxa(transferenciaDTO));
        transferenciaDTO.setTaxa(calculaValorTaxa(transferenciaDTO));

        transferenciaRepository.save(transferencia);
        return transferenciaDTO;
    }

    private BigDecimal calculaValorTaxa(TransferenciaDTO transferencia) {
        BigDecimal conversaoPorcentagem = BigDecimal.valueOf(getPorcentagemTaxa(transferencia));
        return transferencia.getValor().multiply(conversaoPorcentagem).divide(BigDecimal.valueOf(100));
    }

    private static Double getPorcentagemTaxa(TransferenciaDTO transferencia) {
        Double[] tabelaTaxa = {2.5d, 0d, 8.2d, 6.9d, 4.7d, 1.7d};
        LocalDate dataAgendamento = transferencia.getDataAgendamento();
        LocalDate dataTransferencia = transferencia.getDataTransferencia();
        Long dias = Duration.between(dataAgendamento.atStartOfDay(), dataTransferencia.atStartOfDay()).toDays();
        int faxaTaxa = (dias % 10 == 0) ? dias.intValue() / 10 : round(dias / 10) + 1;

        return tabelaTaxa[faxaTaxa];
    }

    private void validaCliente(TransferenciaDTO transferencia) {

        boolean clienteOrigem = clienteRepository.existsByConta(transferencia.getContaOrigem());
        boolean clienteDestino = clienteRepository.existsByConta(transferencia.getContaDestino());
        if (!(clienteOrigem && clienteDestino))
            throw new PreenchimentoIncorretoException("Conta de origem ou de destino não existente. Verificar digitos");
    }

    @Override
    public List<TransferenciaDTO> getBankStatment(String conta) {
        List<Transferencia> transferencias = transferenciaRepository.findByContaOrigem(conta);

        List<TransferenciaDTO> transferenciasDTO = new ArrayList<>();
        ModelMapper mapper = new ModelMapper();

        transferencias.forEach(transferencia ->{
            TransferenciaDTO transferenciaDTO = new TransferenciaDTO();
            mapper.map(transferencia, transferenciaDTO);
            transferenciasDTO.add(transferenciaDTO);
        });

        return transferenciasDTO;
    }

}
