package org.aviatrip.customerservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aviatrip.customerservice.dto.response.DetailedFlightSeatForTicketView;
import org.aviatrip.customerservice.dto.response.FlightSeatReservationView;
import org.aviatrip.customerservice.dto.response.error.ErrorResponse;
import org.aviatrip.customerservice.exception.InternalServerErrorException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;

import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FlightSeatValidator {

    private final Validator validator;

    public void validate(FlightSeatReservationView view) {
        Errors errors = new BeanPropertyBindingResult(view, view.getClass().getSimpleName());
        validator.validate(view, errors);

        checkErrors(errors);
    }

    public void validate(DetailedFlightSeatForTicketView view) {
        Errors errors = new BeanPropertyBindingResult(view, view.getClass().getSimpleName());
        validator.validate(view, errors);

        checkErrors(errors);
    }

    private void checkErrors(Errors errors) {
        if(!errors.hasErrors())
            return;

        Map<String, String> errorMap = errors.getFieldErrors()
                .stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));

        log.error("Error occurred: {} ", errorMap);
        throw new InternalServerErrorException(ErrorResponse.of("invalid data"));
    }
}
