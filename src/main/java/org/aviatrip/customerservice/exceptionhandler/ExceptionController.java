package org.aviatrip.customerservice.exceptionhandler;


import org.aviatrip.customerservice.dto.response.error.ErrorResponse;
import org.aviatrip.customerservice.dto.response.error.ErrorsResponse;
import org.aviatrip.customerservice.exception.BadRequestException;
import org.aviatrip.customerservice.exception.InternalServerErrorException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorsResponse> handleNotValidMethodArgument(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        for(FieldError e : ex.getFieldErrors()) {
            errors.put(e.getField(), e.getDefaultMessage());
        }

        ErrorsResponse errorsResponse = ErrorsResponse.builder()
                .errorMessages(errors)
                .details("invalid field values")
                .build();

        return ResponseEntity.badRequest().body(errorsResponse);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException ex) {
        return ResponseEntity.badRequest()
                .body(ex.getErrorResponse().orElse(
                    ErrorResponse.builder()
                            .errorMessage("bad request")
                            .build()
                ));
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<ErrorResponse> handleInternalServerErrorException(InternalServerErrorException ex) {
        return ResponseEntity.internalServerError()
                .body(ex.getErrorResponse().orElse(
                        ErrorResponse.builder()
                                .errorMessage("internal server error occurred")
                                .details("please try later")
                                .build()
                ));
    }

//    @ExceptionHandler(AuthenticationException.class)
//    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException ex) {
//        System.out.println(ex.getMessage());
//        return ResponseEntity.badRequest().body(new ErrorResponse("incorrect username or password"));
//    }
}