package com.example.online_safari.controller;

import com.example.online_safari.dto.BookingStatusDto;
import com.example.online_safari.dto.BookinngDto;
import com.example.online_safari.model.Bookings;
import com.example.online_safari.services.BookingService;
import com.example.online_safari.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/create-update")
    public ResponseEntity<?> createBooking(@RequestBody BookinngDto bookinngDto){
        Response<Bookings> response =  bookingService.createUpdateBooking(bookinngDto);

        return  ResponseEntity.ok()
                .body(response);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getBookings(@RequestParam(value = "page", defaultValue = "0")Integer page,
                                         @RequestParam(value = "size", defaultValue = "25")Integer size){
        PageRequest pageRequest = PageRequest.of(page,size);

        Page<Bookings> bookingsPage =  bookingService.getAllBookings(pageRequest);

        return ResponseEntity.ok()
                .body(bookingsPage);
    }

    @GetMapping("/get-my-bookings")
    public ResponseEntity<?> getMyBookings(@RequestParam(value = "page",defaultValue = "0")Integer page,
                                           @RequestParam(value = "size", defaultValue = "25")Integer size){
        PageRequest pageRequest =  PageRequest.of(page,size);
        Page<Bookings>  bookingsPage = bookingService.getMyBookings(pageRequest);

        return ResponseEntity.ok()
                .body(bookingsPage);
    }

    @PutMapping("/update-booking")
    public ResponseEntity<?>  updateBookings(@RequestBody BookingStatusDto bookingStatusDto){
        Response<Bookings> response =  bookingService.updateBookingStatus(bookingStatusDto);

        return ResponseEntity.ok().body(response);
    }

}
