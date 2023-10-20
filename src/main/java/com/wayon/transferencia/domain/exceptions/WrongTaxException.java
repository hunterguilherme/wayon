package com.wayon.transferencia.domain.exceptions;


public class WrongTaxException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public WrongTaxException(String mensagem) {
        super(mensagem);
    }


}
