package org.aviatrip.customerservice.decoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aviatrip.customerservice.dto.response.error.ErrorResponse;
import org.aviatrip.customerservice.dto.response.error.ResourceNotFoundResponse;
import org.aviatrip.customerservice.exception.ResourceNotFoundException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class NotFoundDecoder implements ErrorHandler {
    @Override
    public Exception handle(InputStream inputStream) {
        try {
            ErrorResponse response = new ObjectMapper().readValue(inputStream, ErrorResponse.class);
            return new ResourceNotFoundException(response);
        } catch (IOException ex) {
            return new ResourceNotFoundException(ResourceNotFoundResponse.DEFAULT);
        }
    }

    @Override
    public boolean supports(int statusCode) {
        return statusCode == 404;
    }
}
