package org.aviatrip.customerservice.decoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aviatrip.customerservice.dto.response.error.ErrorResponse;
import org.aviatrip.customerservice.exception.InternalServerErrorException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class InternalServerErrorDecoder implements ErrorHandler {
    @Override
    public Exception handle(InputStream inputStream) {
        try {
            ErrorResponse response = new ObjectMapper().readValue(inputStream, ErrorResponse.class);
            return new InternalServerErrorException(response);
        } catch (IOException ex) {
            return new InternalServerErrorException();
        }
    }

    @Override
    public boolean supports(int statusCode) {
        return statusCode == 500;
    }
}
