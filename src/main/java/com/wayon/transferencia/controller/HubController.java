package com.wayon.transferencia.controller;


import com.wayon.transferencia.domain.model.dto.FinancialTransfereDTO;
import com.wayon.transferencia.domain.serviceImpl.HubServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

//@Authorize
@RestController
@RequestMapping(value = "/wayon")
public class HubController {
    @Autowired
    private HubServiceImpl hubServiceImpl;
    @ApiOperation(value = "transferencia financeira ", nickname = "realizaTransferencia", notes = "realiza transferencia financeira", response = FinancialTransfereDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Transferencia realizada com sucesso"),
            @ApiResponse(code = 400, message = "Cliente não encontrado")})
    @RequestMapping(consumes = { "application/json" }, produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<FinancialTransfereDTO> scheduleFinancialTransfer(@RequestBody @Valid FinancialTransfereDTO financialTransfereDTO) {
        FinancialTransfereDTO transferenciaAgendada = hubServiceImpl.scheduleFinancialTransfer(financialTransfereDTO);
        return ResponseEntity.ok(transferenciaAgendada);
    }


    @ApiOperation(value = "extrato bancario ", nickname = "getBankStatment", notes = "Lista os agendamentos cadastrados", response = FinancialTransfereDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Extrato retornado com sucesso"),
            @ApiResponse(code = 400, message = "Transferencia não encontrada")})
    @RequestMapping( produces = { "application/json" }, method = RequestMethod.GET)
    public ResponseEntity<List<FinancialTransfereDTO>> getBankStatment(@RequestParam @Valid String conta) {
        List<FinancialTransfereDTO> bankStatment = hubServiceImpl.getBankStatment(conta);
        return ResponseEntity.ok(bankStatment);
    }

}
