package com.example.online_safari.services;

import com.example.online_safari.dto.BookingStatusDto;
import com.example.online_safari.dto.BookinngDto;
import com.example.online_safari.model.Bookings;
import com.example.online_safari.utils.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookingService {

    Response<Bookings> updateBookingStatus(BookingStatusDto bookingStatusDto);
    Response<Bookings> createUpdateBooking(BookinngDto bookinngDto);
    Response<Bookings> getBookByUuid(String uuid);
    Response<Bookings> deleteBooking(String uuid);
    Response<Integer> getBookingCountByStatus(String status);
    Page<Bookings> getMyBookings(Pageable pageable);
    Page<Bookings> getNewOrders(Pageable pageable);
    Page<Bookings> getMyBookingsByStatus(String status, Pageable pageable);
    Page<Bookings> getBookingsByStatus(String status, Pageable pageable);
    Page<Bookings> getAllBookings(Pageable pageable);



}
