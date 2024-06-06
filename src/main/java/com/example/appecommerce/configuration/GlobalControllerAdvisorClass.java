package com.example.appecommerce.configuration;

import com.example.appecommerce.exceptions.CategoryNotFoundException;
import com.example.appecommerce.exceptions.ProductNotFoundException;
import com.example.appecommerce.payload.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

/**
 * In this class we handle exceptions
 */
@ControllerAdvice()
public class GlobalControllerAdvisorClass extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {CategoryNotFoundException.class})
    public ResponseEntity<Object> handleCategoryNotFoundException(CategoryNotFoundException exception) {

        //Create ApiException object
        ApiException body = new ApiException();

        //Set exceptions class
        body.setError(exception.getClass().toString());

        //set message of the exception
        body.setMessage(exception.getMessage());

        //Set status of the exception
        body.setStatus(HttpStatus.NOT_FOUND.value());

        //Set when this exception occurred
        body.setTime(new Date());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Object> handleProductNotFoundException(ProductNotFoundException exception) {
        //Creating ApiException object
        ApiException apiException = new ApiException();

        //Setting properties
        apiException.setError(exception.getClass().toString());
        apiException.setStatus(HttpStatus.NOT_FOUND.value());
        apiException.setTime(new Date());
        apiException.setMessage(exception.getMessage());

        return new ResponseEntity<>(apiException, HttpStatus.NOT_FOUND);
    }

}
