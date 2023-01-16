package ir.maktab.homeservice.config;

import ir.maktab.homeservice.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalMvcExceptionHandler {
    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(Exception ex, WebRequest request) {
            // 1. status --> BAD REQUEST
            // 2. return exception message to user
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(value = CustomExceptionNotFind.class)
    public ResponseEntity<String> handleRuntimeException(CustomExceptionNotFind ex, WebRequest request) {
        // 1. status --> BAD REQUEST
        // 2. return exception message to user
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(value = CustomExceptionSave.class)
    public ResponseEntity<String> handleRuntimeException(CustomExceptionSave ex, WebRequest request) {
        // 1. status --> BAD REQUEST
        // 2. return exception message to user
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(value = CustomExceptionUpdate.class)
    public ResponseEntity<String> handleRuntimeException(CustomExceptionUpdate ex, WebRequest request) {
        // 1. status --> BAD REQUEST
        // 2. return exception message to user
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(value = CustomNotChoosingException.class)
    public ResponseEntity<String> handleRuntimeException(CustomNotChoosingException ex, WebRequest request) {
        // 1. status --> BAD REQUEST
        // 2. return exception message to user
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(value = CustomExceptionInvalid.class)
    public ResponseEntity<String> handleRuntimeException(CustomExceptionInvalid ex, WebRequest request) {
        // 1. status --> BAD REQUEST
        // 2. return exception message to user
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
    @ExceptionHandler(value = CustomExceptionAmount.class)
    public ResponseEntity<String> handleRuntimeException(CustomExceptionAmount ex, WebRequest request) {
        // 1. status --> BAD REQUEST
        // 2. return exception message to user
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
    @ExceptionHandler(value = CustomExceptionOrderType.class)
    public ResponseEntity<String> handleRuntimeException(CustomExceptionOrderType ex, WebRequest request) {
        // 1. status --> BAD REQUEST
        // 2. return exception message to user
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
    @ExceptionHandler(value = ReCaptchaInvalidException.class)
    public ResponseEntity<String> handleRuntimeException(ReCaptchaInvalidException ex, WebRequest request) {
        // 1. status --> BAD REQUEST
        // 2. return exception message to user
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
