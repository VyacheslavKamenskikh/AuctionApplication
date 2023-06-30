package ru.skypro.lessons.springboot.skyproauction.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class LotExceptionHandler {
    @ExceptionHandler(LotNotFoundException.class)
    public ResponseEntity<?> handleNotFound(){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(LotNotStarted.class)
    public ResponseEntity<?> handleNotStarted(){
        return ResponseEntity.badRequest().build();
    }
}
