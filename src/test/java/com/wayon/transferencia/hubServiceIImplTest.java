package com.wayon.transferencia;

import com.wayon.transferencia.domain.exceptions.EntityNotFoundException;
import com.wayon.transferencia.domain.exceptions.WrongTaxException;
import com.wayon.transferencia.domain.model.FinancialTransfer;
import com.wayon.transferencia.domain.model.dto.FinancialTransfereDTO;
import com.wayon.transferencia.domain.repository.ClienteRepository;
import com.wayon.transferencia.domain.repository.TransferenciaRepository;
import com.wayon.transferencia.domain.serviceImpl.HubServiceImpl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class hubServiceIImplTest {
    @InjectMocks
    private HubServiceImpl hubService;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private TransferenciaRepository transferenciaRepository;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
        hubService = new HubServiceImpl(clienteRepository, transferenciaRepository);
    }

    @Test
    public void shouldTransferWhenValidAccount() {
        FinancialTransfereDTO validFinancialTransfereDTO = new FinancialTransfereDTO();
        validFinancialTransfereDTO.setContaOrigem("1234567890");
        validFinancialTransfereDTO.setValor(BigDecimal.valueOf(100));
        validFinancialTransfereDTO.setDataAgendamento(LocalDate.of(2023, 10, 10));
        validFinancialTransfereDTO.setDataTransferencia(LocalDate.of(2023, 10, 31));
        validFinancialTransfereDTO.setContaDestino("1111111111");

        when(clienteRepository.existsByConta("1234567890")).thenReturn(true);
        when(clienteRepository.existsByConta("1111111111")).thenReturn(true);
        FinancialTransfer financialTransfer = new FinancialTransfer(
                null,
                "1234567890",
                "1111111111",
                BigDecimal.valueOf(100),
                BigDecimal.valueOf(8.2),
                LocalDate.of(2023, 10, 31),
                LocalDate.of(2023, 10, 10));
        when(transferenciaRepository.save(financialTransfer)).thenReturn(financialTransfer);
        FinancialTransfereDTO result = hubService.scheduleFinancialTransfer(validFinancialTransfereDTO);
        assertNotNull(result);
        assertEquals("1234567890", result.getContaOrigem());
        assertEquals("1111111111", result.getContaDestino());

    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldFailWhenOriginAccountDoesNotExist() {
        FinancialTransfereDTO validFinancialTransfereDTO = new FinancialTransfereDTO();
        validFinancialTransfereDTO.setContaOrigem("1234567890");
        validFinancialTransfereDTO.setContaDestino("1111111111");

        when(clienteRepository.existsByConta("1234567890")).thenReturn(false);
        when(clienteRepository.existsByConta("1111111111")).thenReturn(true);

        hubService.scheduleFinancialTransfer(validFinancialTransfereDTO);

    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldFailWhenDestinyAccountDoesNotExist() {
        FinancialTransfereDTO validFinancialTransfereDTO = new FinancialTransfereDTO();
        validFinancialTransfereDTO.setContaOrigem("1234567890");
        validFinancialTransfereDTO.setContaDestino("1111111111");

        when(clienteRepository.existsByConta("1234567890")).thenReturn(true);
        when(clienteRepository.existsByConta("1111111111")).thenReturn(false);

        hubService.scheduleFinancialTransfer(validFinancialTransfereDTO);

    }

    @Test(expected = WrongTaxException.class)
    public void shouldFailIfTaxEqualsZero() {
        FinancialTransfereDTO validFinancialTransfereDTO = new FinancialTransfereDTO();
        validFinancialTransfereDTO.setContaOrigem("1234567890");
        validFinancialTransfereDTO.setValor(BigDecimal.valueOf(100));
        validFinancialTransfereDTO.setDataAgendamento(LocalDate.of(2023, 10, 10));
        validFinancialTransfereDTO.setDataTransferencia(LocalDate.of(2023, 10, 20));
        validFinancialTransfereDTO.setContaDestino("1111111111");

        when(clienteRepository.existsByConta("1234567890")).thenReturn(true);
        when(clienteRepository.existsByConta("1111111111")).thenReturn(true);

        hubService.scheduleFinancialTransfer(validFinancialTransfereDTO);


    }

    @Test
    public void shouldReturnBankStatmentList() {
        String conta = "123456";
        FinancialTransfer validFinancialTransfer = new FinancialTransfer();
        List<FinancialTransfer> validFinancialTransferList = new ArrayList<>();
        validFinancialTransfer.setContaOrigem("1234567890");
        validFinancialTransfer.setValor(BigDecimal.valueOf(100));
        validFinancialTransfer.setDataAgendamento(LocalDate.of(2023, 10, 10));
        validFinancialTransfer.setDataTransferencia(LocalDate.of(2023, 10, 20));
        validFinancialTransfer.setContaDestino("1111111111");
        validFinancialTransferList.add(validFinancialTransfer);

        when(transferenciaRepository.findByContaOrigem(conta)).thenReturn(validFinancialTransferList);
        when(clienteRepository.existsByConta("1111111111")).thenReturn(true);

        List<FinancialTransfereDTO> result = hubService.getBankStatment(conta);

        assertNotNull(result);

        assertFalse(result.isEmpty());

        FinancialTransfereDTO firstItem = result.get(0);
        assertEquals("1234567890", firstItem.getContaOrigem());
        assertEquals("1111111111", firstItem.getContaDestino());
        assertEquals(BigDecimal.valueOf(100), firstItem.getValor());
        assertEquals(LocalDate.of(2023, 10, 10), firstItem.getDataAgendamento());
        assertEquals(LocalDate.of(2023, 10, 20), firstItem.getDataTransferencia());
    }
}