package com.example.hotel.NotificationService;

import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    public void onNewBooking(String bookingId) {
        System.out.println("Received new booking from 52-1234: " + bookingId + " Your_Name_52_1234");
    }
    
}
