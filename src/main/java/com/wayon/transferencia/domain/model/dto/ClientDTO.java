package com.wayon.transferencia.domain.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ClientDTO {

    private String nome;
    private String conta;
    private BigDecimal saldo;

}
