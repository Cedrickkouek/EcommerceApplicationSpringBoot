package com.business.brendaapp.advice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.business.brendaapp.exception.ProductNotFoundException;

import com.business.brendaapp.exception.InvalidCategoryException;
import com.business.brendaapp.exception.CommandeNotFoundException;


@RestControllerAdvice
public class ApplicationExceptionHandler {
    
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> gererLesArgumentsInvalid(MethodArgumentNotValidException excep)
    {
        Map<String, String> errorMap = new HashMap<>();
        excep.getBindingResult().getFieldErrors().forEach(erreur -> {
            errorMap.put(erreur.getField(), erreur.getDefaultMessage());
        });
        return errorMap;
    }
    
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(InvalidCategoryException.class)
    public Map<String, String> gererExceptionsDesLicences(InvalidCategoryException excep)
    {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("Message D'erreur", excep.getMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ProductNotFoundException.class)
    public Map<String, String> ProductNotFoundException(ProductNotFoundException excep)
    {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("Message D'erreur", excep.getMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(CommandeNotFoundException.class)
    public Map<String, String> CommandeNotFoundException(CommandeNotFoundException excep)
    {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("Message D'erreur", excep.getMessage());
        return errorMap;
    }

    

}
