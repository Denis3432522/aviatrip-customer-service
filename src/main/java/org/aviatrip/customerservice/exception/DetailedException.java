package org.aviatrip.customerservice.exception;

import org.aviatrip.customerservice.dto.response.error.ErrorResponse;

import java.util.Optional;

public interface DetailedException {

    Optional<ErrorResponse> getErrorResponse();
}
