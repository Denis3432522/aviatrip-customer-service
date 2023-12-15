package org.aviatrip.customerservice.decoder;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aviatrip.customerservice.dto.response.error.ErrorResponse;
import org.aviatrip.customerservice.exception.InternalServerErrorException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DefaultFeignErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder errorDecoder = new Default();
    private final List<ErrorHandler> errorHandlers;

    @Override
    public Exception decode(String methodKey, Response response) {
        final InputStream inputStream;

        try {
            if(response.body() == null)
                inputStream = InputStream.nullInputStream();
            else
                inputStream = response.body().asInputStream();
        } catch (IOException ex) {
            throw new InternalServerErrorException(ErrorResponse.of(ex.getMessage()));
        }

        for(ErrorHandler errorHandler : errorHandlers) {
            if(errorHandler.supports(response.status()))
                return errorHandler.handle(inputStream);
        }

        return errorDecoder.decode(methodKey, response);
    }
}
