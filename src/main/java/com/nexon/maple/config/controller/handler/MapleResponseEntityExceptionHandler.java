package com.nexon.maple.config.controller.handler;

import com.nexon.maple.config.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class MapleResponseEntityExceptionHandler {

    //정의되지 않은 Exception
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ResponseDTO> handleAllExceptions(Exception ex) {
        ResponseDTO exceptionResponse = ResponseDTO.ofFail(ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
    }

    //잘못 입력된 Exception
    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<ResponseDTO> handleIllegalArgumentException(Exception ex) {
        ResponseDTO exceptionResponse = ResponseDTO.ofFail(ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(exceptionResponse);
    }

    //Controller ParameterError
    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ResponseDTO> handleMethodArgumentTypeMismatchExceptionException(MethodArgumentTypeMismatchException ex) {
        ex.printStackTrace();
        ResponseDTO exceptionResponse = ResponseDTO.ofFail(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }
}
