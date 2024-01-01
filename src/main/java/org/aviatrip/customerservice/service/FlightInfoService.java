package org.aviatrip.customerservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.aviatrip.customerservice.dto.response.DetailedFlightSeatForTicketView;
import org.aviatrip.customerservice.entity.FlightInfo;
import org.aviatrip.customerservice.repository.FlightInfoRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FlightInfoService {

    private final FlightInfoRepository flightInfoRepository;

    public Optional<FlightInfo> getFlightInfo(UUID flightId) {
        return flightInfoRepository.findById(flightId);
    }

    public boolean existsFlightInfo(UUID flightId) {
        return flightInfoRepository.existsById(flightId);
    }

    @Transactional
    public FlightInfo createFlightInfo(DetailedFlightSeatForTicketView view) {
        return flightInfoRepository.save(FlightInfo.builder()
                .id(view.getFlightId())
                .source(view.getFlightSource())
                .destination(view.getFlightDestination())
                .departureTimestamp(view.getFlightDepartureTimestamp())
                .arrivalTimestamp(view.getFlightArrivalTimestamp())
                .companyName(view.getCompanyName())
                .airplaneModel(view.getAirplaneModel())
                .build()
        );
    }
}
