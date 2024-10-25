package org.example.smartcabs.service;

import org.example.smartcabs.dto.BookingRequest;
import org.example.smartcabs.dto.TripInfo;
import org.example.smartcabs.model.Trip;
import org.example.smartcabs.model.User;
import org.example.smartcabs.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TripService {

    @Autowired
    TripRepository tripRepository;

    @Autowired
    LocationService locationService;

    public TripInfo processTrip(BookingRequest bookingRequest, User user) {
        Trip newTrip = new Trip();
        newTrip.setUser(user);
        newTrip.setCab(locationService.findNearestCab(
                bookingRequest.getSrcLocation().getLatitude(), bookingRequest.getSrcLocation().getLongitude()));
        newTrip.setStartLocation(
                locationService.findLocation(
                        bookingRequest.getSrcLocation().getLatitude(), bookingRequest.getSrcLocation().getLongitude()));
        tripRepository.save(newTrip);
        return tripToTripInfo(newTrip);
    }

    private TripInfo tripToTripInfo(Trip trip) {
        TripInfo tripInfo = new TripInfo();
        tripInfo.setStartLocation(trip.getStartLocation());
        tripInfo.setCarCompany(trip.getCab().getCar().getCarCompany());
        tripInfo.setCarType(trip.getCab().getCar().getCarType());
        tripInfo.setNumberPlate(trip.getCab().getCar().getNumberPlate());
        tripInfo.setCabCurrentLocation(trip.getCab().getCurrentLocation());

        return tripInfo;
    }
}
