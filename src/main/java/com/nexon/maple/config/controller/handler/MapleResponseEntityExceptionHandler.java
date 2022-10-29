package com.nexon.maple.config.controller.handler;

import com.nexon.maple.config.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MapleResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ResponseDTO> handleAllExceptions(Exception ex) {
        ResponseDTO exceptionResponse = ResponseDTO.ofFail(ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<ResponseDTO> handleIllegalArgumentException(Exception ex) {
        ResponseDTO exceptionResponse = ResponseDTO.ofFail(ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(exceptionResponse);
    }
}
