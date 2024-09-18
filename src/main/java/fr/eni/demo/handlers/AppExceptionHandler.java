package fr.eni.demo.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> capturerMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        /*
         * Une pirouette pour concaténer tous les messages d'erreurs liés à la validation de nos BO
         */
        String message = ex
                .getFieldErrors()
                .stream()
                .map(e -> e.getDefaultMessage())
                .reduce("Error(s) : ", String::concat);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> capturerException(Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
    }
}
