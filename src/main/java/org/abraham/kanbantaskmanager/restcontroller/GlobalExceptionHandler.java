//package org.abraham.kanbantaskmanager.restcontroller;
//
//import org.abraham.kanbantaskmanager.Exceptions.DuplicateNameException;
//import org.abraham.kanbantaskmanager.Exceptions.EntityNotFoundException;
//import org.abraham.kanbantaskmanager.dtos.RestDtos.Error;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.http.converter.HttpMessageNotReadableException;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@ControllerAdvice
//public class GlobalExceptionHandler {
//
//
//    @ExceptionHandler(EntityNotFoundException.class)
//    public ResponseEntity<Error> handleEntityNotFoundException(EntityNotFoundException ex) {
//        var error = new Error();
//        error.setMessage(ex.getMessage());
//        error.setCode(HttpStatus.BAD_REQUEST.value());
//        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(DuplicateNameException.class)
//    public ResponseEntity<Error> handleDuplicateNameException(DuplicateNameException e) {
//        var error = new Error();
//        error.setMessage(e.getMessage());
//        error.setCode(HttpStatus.BAD_REQUEST.value());
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
//    }
//
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<Map<String,String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
//        var errors = new HashMap<String, String>();
//
//        ex.getBindingResult().getFieldErrors().forEach((error) -> {
//            errors.put(error.getField(),error.getDefaultMessage());
//        });
//
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
//    }
//
//    @ExceptionHandler(HttpMessageNotReadableException.class)
//    public ResponseEntity<Error> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
//        var error = new Error();
//        error.setMessage(ex.getMessage());
//        error.setCode(HttpStatus.BAD_REQUEST.value());
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
//    }
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Error> handleException(Exception ex) {
//
//        var error = new Error();
//        error.setMessage(ex.getMessage());
//        error.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
//    }
//}
