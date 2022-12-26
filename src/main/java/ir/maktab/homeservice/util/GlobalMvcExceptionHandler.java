package ir.maktab.homeservice.util;

import ir.maktab.homeservice.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalMvcExceptionHandler {
    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity handleRuntimeException(Exception ex, WebRequest request) {
            // 1. status --> BAD REQUEST
            // 2. return exception message to user
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(value = CustomExceptionNotFind.class)
    public ResponseEntity handleRuntimeException(CustomExceptionNotFind ex, WebRequest request) {
        // 1. status --> BAD REQUEST
        // 2. return exception message to user
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(value = CustomExceptionSave.class)
    public ResponseEntity handleRuntimeException(CustomExceptionSave ex, WebRequest request) {
        // 1. status --> BAD REQUEST
        // 2. return exception message to user
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(value = CustomExceptionUpdate.class)
    public ResponseEntity handleRuntimeException(CustomExceptionUpdate ex, WebRequest request) {
        // 1. status --> BAD REQUEST
        // 2. return exception message to user
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(value = CustomNotChoosingException.class)
    public ResponseEntity handleRuntimeException(CustomNotChoosingException ex, WebRequest request) {
        // 1. status --> BAD REQUEST
        // 2. return exception message to user
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(value = CustomExceptionInvalid.class)
    public ResponseEntity CustomPatternInvalidException(Exception ex, WebRequest request) {
        // 1. status --> BAD REQUEST
        // 2. return exception message to user
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

}