package com.wayon.transferencia.ExceptionHandler;


import com.wayon.transferencia.domain.exceptions.EntityNotFoundException;
import com.wayon.transferencia.domain.exceptions.WrongTaxException;
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

    @ExceptionHandler(WrongTaxException.class)
    public ResponseEntity<?> handlePreenchimentoIncorretoException(WrongTaxException e) {

        ApiErrorMessage apiErrorMessage = new ApiErrorMessage(e.getMessage());

        return ResponseEntity.status(BAD_REQUEST).body(apiErrorMessage);
    }


    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntidadeNaoEncontradaExceptionn(EntityNotFoundException e) {

        ApiErrorMessage apiErrorMessage = new ApiErrorMessage(e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiErrorMessage);
    }


}
