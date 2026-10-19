package mx.edu.utez.productos.exception;

import java.util.stream.Collectors;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.Map;


@RestControllerAdvice 
public class GlobalExceptionHandler{


    @ExceptionHandler 
    public ResponseEntity<String> handleNotFound(
        ResoureceNotFoundException ex
    ){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(
        MethodArgumentNotValidException ex) {


    Map<String, String> errors = ex.getBindingResult()
        .getFieldErrors()
        .stream()
        .collect(Collectors.toMap(
            error -> error.getField(),
            error -> error.getDefaultMessage(),
            (first, second) -> first
        ));


    return ResponseEntity
        .badRequest()
        .body(errors);
}

    @ExceptionHandler(InsuficienteStockException.class)
    public ResponseEntity<String>handleInsuficienteStock(InsuficienteStockException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

}