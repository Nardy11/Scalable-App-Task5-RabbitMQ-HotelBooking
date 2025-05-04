package com.example.hotel.BookingService.Services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.hotel.BookingService.Clients.AvailabilityClient;
import com.example.hotel.BookingService.rabbitmq.BookingProducer;

@Service
public class BookingService {
    private final AvailabilityClient availability;
    private final BookingProducer producer;

    @Value("${Name}")
    String name;

    @Value("${ID}")
    String id;

    public BookingService(AvailabilityClient availability,
                          BookingProducer producer) {
        this.availability = availability;
        this.producer     = producer;
    }
    public String createBooking(String roomType, int nights) {
    boolean isAvailable = availability.check(roomType, nights);

    if (!isAvailable) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Room not available");
    }

    String bookingId = UUID.randomUUID().toString();
    String fullMessage = bookingId + " " + name + "_" + id;
    producer.sendBooking(bookingId);
    return fullMessage;
}
}

