package com.wayon.transferencia.controller;


import com.wayon.transferencia.domain.model.dto.TransferenciaDTO;
import com.wayon.transferencia.domain.serviceImpl.hubServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@Authorize
@RestController
@RequestMapping(value = "/wayon")
public class hubController {
    @Autowired
    private hubServiceImpl hubServiceImpl;
    @ApiOperation(value = "transferencia financeira ", nickname = "realizaTransferencia", notes = "realiza transferencia financeira", response = TransferenciaDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Transferencia realizada com sucesso", response = TransferenciaDTO.class),
            @ApiResponse(code = 400, message = "Cliente não encontrado")})
    @RequestMapping(consumes = { "application/json" }, produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<?> insereTransferencia(@RequestBody @Valid TransferenciaDTO transferenciaDTO) {
        TransferenciaDTO transferenciaAgendada = hubServiceImpl.agendaTransferencia(transferenciaDTO);
        return ResponseEntity.ok(transferenciaAgendada);
    }


    @ApiOperation(value = "extrato bancario ", nickname = "getBankStatment", notes = "Lista os agendamentos cadastrados", response = TransferenciaDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Extrato retornado com sucesso", response = TransferenciaDTO.class),
            @ApiResponse(code = 400, message = "Transferencia não encontrada")})
    @RequestMapping(consumes = { "application/json" }, produces = { "application/json" }, method = RequestMethod.GET)
    public ResponseEntity<?> getBankStatment(@RequestParam @Valid String conta) {
        List<TransferenciaDTO> bankStatment = hubServiceImpl.getBankStatment(conta);
        return ResponseEntity.ok(bankStatment);
    }

//
//    @GetMapping("/extrato")
//    public class ExtratoController {
//        @Autowired
//        private ExtratoService extratoService;
//
//        @GetMapping
//        public ResponseEntity<List<ExtratoItem>> obterExtrato() {
//            List<ExtratoItem> extrato = extratoService.obterExtrato();
//            return ResponseEntity.ok(extrato);
//        }
//    }
}
