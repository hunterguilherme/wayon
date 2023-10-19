package com.wayon.transferencia.ExceptionHandler;


import com.wayon.transferencia.domain.exceptions.EntidadeNaoEncontradaException;
import com.wayon.transferencia.domain.exceptions.PreenchimentoIncorretoException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
@Slf4j
public class BatidaHandlerException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(PreenchimentoIncorretoException.class)
    public ResponseEntity<?> handlePreenchimentoIncorretoException(PreenchimentoIncorretoException e) {

        ApiErrorMessage apiErrorMessage = new ApiErrorMessage(e.getMessage());

        return ResponseEntity.status(BAD_REQUEST).body(apiErrorMessage);
    }


    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> handleEntidadeNaoEncontradaExceptionn(EntidadeNaoEncontradaException e) {

        ApiErrorMessage apiErrorMessage = new ApiErrorMessage(e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiErrorMessage);
    }


}
