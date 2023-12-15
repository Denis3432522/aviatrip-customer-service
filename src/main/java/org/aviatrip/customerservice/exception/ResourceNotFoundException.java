package org.aviatrip.customerservice.exception;

import org.aviatrip.customerservice.dto.response.error.ErrorResponse;

import java.util.Optional;

public class ResourceNotFoundException extends RuntimeException implements DetailedException {

    private ErrorResponse errorResponse;

    public ResourceNotFoundException() {}

    public ResourceNotFoundException(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }

    public Optional<ErrorResponse> getErrorResponse() {
        return Optional.ofNullable(errorResponse);
    }
}
