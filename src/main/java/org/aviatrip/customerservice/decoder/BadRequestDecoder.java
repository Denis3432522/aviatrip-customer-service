package org.aviatrip.customerservice.decoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aviatrip.customerservice.dto.response.error.ErrorResponse;
import org.aviatrip.customerservice.dto.response.error.ErrorsResponse;
import org.aviatrip.customerservice.exception.BadRequestException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;

@Component
public class BadRequestDecoder implements ErrorHandler {
    @Override
    public Exception handle(InputStream inputStream) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return new BadRequestException(mapper.readValue(inputStream, ErrorResponse.class));
        } catch (IOException ex) {}

        ErrorsResponse errorsResponse;

        try {
            errorsResponse = mapper.readValue(inputStream, ErrorsResponse.class);
        } catch (IOException ex) {
            return new BadRequestException(null);
        }

        Iterator<Map.Entry<String, String>> iterator = errorsResponse.getErrorMessages().entrySet().iterator();

        if(!iterator.hasNext())
            return new BadRequestException(null);

        Map.Entry<String, String> entry = iterator.next();

        return new BadRequestException(ErrorResponse.builder()
                .errorMessage(entry.getKey() + ": " + entry.getValue())
                .details(errorsResponse.getDetails())
                .build()
        );
    }

    @Override
    public boolean supports(int statusCode) {
        return statusCode == 400;
    }
}
