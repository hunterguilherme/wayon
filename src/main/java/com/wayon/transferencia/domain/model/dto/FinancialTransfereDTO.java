package com.wayon.transferencia.domain.model.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;


@Getter
@Setter
public class FinancialTransfereDTO {
    @ApiModelProperty(notes = "Conta de origem", example = "1234567890", required = true)
    private String contaOrigem;
    @ApiModelProperty(notes = "Conta de destino", example = "1111111111", required = true)
    private String contaDestino;
    @ApiModelProperty(notes = "Valor a ser Transferido", example = "100", required = true)
    private BigDecimal valor;
    @ApiModelProperty(notes = "Valor da taxa", example = "6.9", required = true)
    private BigDecimal taxa;
    @ApiModelProperty(notes = "Data da transferencia", example = "2023-10-31", required = true)
    private LocalDate dataTransferencia;
    @ApiModelProperty(notes = "Data do agendamento", example = "2023-10-01", required = true)
    private LocalDate dataAgendamento;
}
