package demo.currency.controller;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class CommonExceptionHandler
{
    @ResponseStatus( HttpStatus.BAD_REQUEST )
    @ExceptionHandler( MethodArgumentNotValidException.class )
    public Map< String, String > handleValidationExceptions( MethodArgumentNotValidException ex )
    {
        Map< String, String > errors = new HashMap<>();
        for ( var error: ex.getBindingResult().getFieldErrors() )
        {
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();
            errors.put( fieldName, errorMessage );
        }
        return errors;
    }

    @ResponseStatus( HttpStatus.BAD_REQUEST )
    @ExceptionHandler( ConstraintViolationException.class )
    public Map< String, String > handleConstraintViolationExceptions( ConstraintViolationException ex )
    {
        Map< String, String > errors = new HashMap<>();
        for ( var violation: ex.getConstraintViolations() )
        {
            String fieldName = ( ( PathImpl ) violation.getPropertyPath() ).getLeafNode().getName();
            String errorMessage = violation.getMessage();
            errors.put( fieldName, errorMessage );
        }
        return errors;
    }
}