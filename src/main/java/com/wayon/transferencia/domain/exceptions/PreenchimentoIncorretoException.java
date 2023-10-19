package com.wayon.transferencia.domain.exceptions;


public class PreenchimentoIncorretoException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public PreenchimentoIncorretoException(String mensagem) {
        super(mensagem);
    }


}
