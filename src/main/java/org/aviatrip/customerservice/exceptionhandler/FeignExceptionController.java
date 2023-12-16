package org.aviatrip.customerservice.exceptionhandler;

import feign.FeignException;
import feign.codec.DecodeException;
import lombok.extern.slf4j.Slf4j;
import org.aviatrip.customerservice.dto.response.error.ErrorResponse;
import org.aviatrip.customerservice.dto.response.error.InternalErrorResponse;
import org.aviatrip.customerservice.util.LoggerMessagePreparer;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class FeignExceptionController {

    @ExceptionHandler(FeignException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleDataIntegrityViolationException(FeignException ex) {
        int statusCode = ex.status() == -1 ? 500 : ex.status();
        log.error(LoggerMessagePreparer.prepareErrorMessage(ex, HttpStatus.valueOf(statusCode)));

        return InternalErrorResponse.DEFAULT;
    }

    @ExceptionHandler(DecodeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleDecodeException(DecodeException ex) {
        log.error(LoggerMessagePreparer.prepareErrorMessage(ex, HttpStatus.INTERNAL_SERVER_ERROR));

        return InternalErrorResponse.DEFAULT;
    }
}
