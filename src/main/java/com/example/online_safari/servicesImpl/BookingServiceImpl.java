package com.example.online_safari.servicesImpl;

import com.example.online_safari.dto.BookingStatusDto;
import com.example.online_safari.dto.BookinngDto;
import com.example.online_safari.model.Bookings;
import com.example.online_safari.model.Safari;
import com.example.online_safari.model.UserAccount;
import com.example.online_safari.repositories.BookingRepository;
import com.example.online_safari.repositories.SafariRepository;
import com.example.online_safari.repositories.UserAccountRepository;
import com.example.online_safari.services.BookingService;
import com.example.online_safari.utils.BookingStatus;
import com.example.online_safari.utils.Response;
import com.example.online_safari.utils.ResponseCode;
import com.example.online_safari.utils.userextractor.LoggedUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;


@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private BookingRepository bookingRepository;


    @Autowired
    private LoggedUser loggedUser;

    @Autowired
    private SafariRepository safariRepository;


    private Logger logger = LoggerFactory.getLogger(BookingServiceImpl.class);


    @Override
    public Response<Bookings> updateBookingStatus(BookingStatusDto bookingStatusDto) {

        try {

            UserAccount user  =   loggedUser.getUser();

            if(user == null){
                return new Response<>(true, ResponseCode.UNAUTHORIZED,"Unauthorized");
            }



            Optional<Bookings> optionalBooking = bookingRepository.findFirstByUuid(bookingStatusDto.getBookingUuid());

            if(optionalBooking.isEmpty())
                return new Response<>(true, ResponseCode.NO_RECORD_FOUND,"Booking record not found");

            Bookings booking = optionalBooking.get();

            if(bookingStatusDto.getStatus().equalsIgnoreCase("CANCELLED") && booking.getStatus().equals(BookingStatus.DELIVERED))
                return new Response<>(true,ResponseCode.BAD_REQUEST,"You can not cancel an already delivered service");

            if(bookingStatusDto.getStatus().equalsIgnoreCase("CANCELLED"))
                booking.setStatus(BookingStatus.CANCELED.toString());
            if(bookingStatusDto.getStatus().equalsIgnoreCase("SUBMITTED"))
                booking.setStatus(BookingStatus.SUBMITTED.toString());
            if(bookingStatusDto.getStatus().equalsIgnoreCase("CONFIRMED"))
                booking.setStatus(BookingStatus.CONFIRMED.toString());
            if(bookingStatusDto.getStatus().equalsIgnoreCase("RECEIVED"))
                booking.setStatus(BookingStatus.RECEIVED.toString());
            if(bookingStatusDto.getStatus().equalsIgnoreCase("DELIVERED"))
                booking.setStatus(BookingStatus.DELIVERED.toString());

            Bookings bookings = bookingRepository.save(booking);

            return new Response<>(false, ResponseCode.SUCCESS,bookings, "Booking status updated");

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new Response<>(true,ResponseCode.FAIL,"Operation failed");
    }

    @Override
    public Response<Bookings> createUpdateBooking(BookinngDto bookinngDto) {
        UserAccount user = loggedUser.getUser();

        if(user == null){
            return new Response<>(true, ResponseCode.UNAUTHORIZED, "UNAUTHORIZED");
        }

        Bookings booking = new Bookings();
        try {
            if(bookinngDto.getSafariUuid() == null){
                return new Response<>(true,ResponseCode.NULL_ARGUMENT,"Latitude is required");
            }
            else {
                Optional<Safari> optionalSafari = safariRepository.findFirstByUuid(bookinngDto.getSafariUuid());

                if(optionalSafari.isPresent()){
                    booking.setSafariUuid(optionalSafari.get());
                }
                else {
                    return new Response<>(true,ResponseCode.NO_RECORD_FOUND,"No record found");
                }
            }


            if(bookinngDto.getPickupd_date() == null){
                return new Response<>(true,ResponseCode.NULL_ARGUMENT,"pickup date is required");
            }

            if(!bookinngDto.getPickupd_date().isBlank() && !Objects.equals(bookinngDto.getPickupd_date(), booking.getPickupDate())){
                booking.setPickupDate(bookinngDto.getPickupd_date());
            }


            if(bookinngDto.getSeats() <0 ){
                return  new Response<>(true,ResponseCode.INVALID_REQUEST,"Seats no out of bound");
            }

            if(bookinngDto.getSafariUuid() == null){
                return new Response<>(true,ResponseCode.NULL_ARGUMENT,"Latitude is required");
            }
            else {
                Optional<Safari> optionalSafari = safariRepository.findFirstByUuid(bookinngDto.getSafariUuid());

                if(optionalSafari.isPresent()){
                    booking.setSafariUuid(optionalSafari.get());

                    booking.setTotalBill(calculateBilledAmount(bookinngDto.getSeats() , optionalSafari.get().getPrice().toString()));
                }
                else {
                    return new Response<>(true,ResponseCode.NO_RECORD_FOUND,"No record found");
                }
            }

            booking.setBookedBy(user);
            booking.setSeats(bookinngDto.getSeats());

            booking.setStatus(String.valueOf(BookingStatus.SUBMITTED));


            Bookings booking2 = bookingRepository.save(booking);

            return new Response<>(false, ResponseCode.SUCCESS, booking2, "Booking placed successfully");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new Response<>(true,ResponseCode.FAIL,"Operation  failed");
    }

    @Override
    public Response<Bookings> getBookByUuid(String uuid) {
        UserAccount user = loggedUser.getUser();

        if(user == null){
            return new Response<>(true,ResponseCode.UNAUTHORIZED, "UNAUTHORIZED");
        }

        try {
            Optional<Bookings> optionalBooking = bookingRepository.findFirstByUuid(uuid);

            if(optionalBooking.isEmpty()){
                return new Response<>(true,ResponseCode.NO_RECORD_FOUND, "Record not found");
            }

            return new Response<>(true,ResponseCode.SUCCESS, optionalBooking.get(), "Record found");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new Response<>(true,ResponseCode.FAIL, "Operation failed");
    }

    @Override
    public Response<Bookings> deleteBooking(String uuid) {
        UserAccount user = loggedUser.getUser();

        if(user == null){
            return new Response<>(true,ResponseCode.UNAUTHORIZED, "UNAUTHORIZED");
        }

        try {
            Optional<Bookings> optionalBooking = bookingRepository.findFirstByUuid(uuid);

            if(optionalBooking.isEmpty())
                return new Response<>(true,ResponseCode.NO_RECORD_FOUND,"No record found");

            Bookings booking = optionalBooking.get();

            bookingRepository.delete(booking);

            return new Response<>(true,ResponseCode.SUCCESS,"Booking deleted successfully");
        }
        catch (Exception  e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Response<Integer> getBookingCountByStatus(String status) {
        return null;
    }

    @Override
    public Page<Bookings> getMyBookings(Pageable pageable) {
        UserAccount user = loggedUser.getUser();

        if(user == null){
            return new PageImpl<>(new ArrayList<>());
        }

        Page<Bookings> bookingsPage = bookingRepository.findAllByBookedByAndStatusNotOrderByCreatedAtDesc(user,BookingStatus.CANCELED.toString(),pageable);

        return bookingsPage;
    }

    @Override
    public Page<Bookings> getNewOrders(Pageable pageable) {

        UserAccount user =  loggedUser.getUser();

        if(user == null){
            return new PageImpl<>(new ArrayList<>());
        }

        Page<Bookings>  bookingsPage = bookingRepository.findAllByStatusOrderByCreatedAtDesc(BookingStatus.RECEIVED,pageable);
        return bookingsPage;
    }

    @Override
    public Page<Bookings> getMyBookingsByStatus(String status, Pageable pageable) {

        try {
            UserAccount user = loggedUser.getUser();

            if(user == null)
                return new PageImpl<>(new ArrayList<>());

            return bookingRepository.findAllByStatusNotAndBookedByOrderByCreatedAtDesc(BookingStatus.CANCELED.toString(),user,pageable);

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new PageImpl<>(new ArrayList<>());
    }

    @Override
    public Page<Bookings> getBookingsByStatus(String status, Pageable pageable) {

        try {
            BookingStatus bookingStatus;

            if (status.equalsIgnoreCase(BookingStatus.CONFIRMED.name()))
                bookingStatus = BookingStatus.CONFIRMED;
            else if (status.equalsIgnoreCase(BookingStatus.SUBMITTED.name()))
                bookingStatus = BookingStatus.SUBMITTED;
            else if (status.equalsIgnoreCase(BookingStatus.ON_PROCESSING.name()))
                bookingStatus = BookingStatus.ON_PROCESSING;
            else if (status.equalsIgnoreCase(BookingStatus.PROCESSED.name()))
                bookingStatus = BookingStatus.PROCESSED;
            else if (status.equalsIgnoreCase(BookingStatus.RECEIVED.name()))
                bookingStatus = BookingStatus.RECEIVED;
            else if (status.equalsIgnoreCase(BookingStatus.DELIVERED.name()))
                bookingStatus = BookingStatus.DELIVERED;
            else if (status.equalsIgnoreCase(BookingStatus.CANCELED.name()))
                bookingStatus = BookingStatus.CANCELED;
            else if (status.equalsIgnoreCase(BookingStatus.RETURNED.name()))
                bookingStatus = BookingStatus.RETURNED;
            else
                bookingStatus = BookingStatus.SUBMITTED;

            return  bookingRepository.findAllByStatusOrderByCreatedAtDesc(bookingStatus,pageable);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new PageImpl<>(new ArrayList<>());
    }

    @Override
    public Page<Bookings> getAllBookings(Pageable pageable) {

        try {
            UserAccount user = loggedUser.getUser();

            if(user == null)
                return new PageImpl<>(new ArrayList<>());

            return bookingRepository.findAllByDeletedFalseAndStatusNotOrderByCreatedAtDesc(BookingStatus.CANCELED.toString(),pageable);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new PageImpl<>(new ArrayList<>());
    }

    private double calculateBilledAmount(double seats, String price) {
        double billedAmount = 0.0;

            double kidsCost = (seats * extractAmount(toString()));
            billedAmount = billedAmount + ( kidsCost);

        return billedAmount;
    }


    public int extractAmount(String price) {
        // Split the string based on space character
        String[] parts = price.split(" ");

        // Check if there are two parts (amount and currency)
        if (parts.length == 2) {
            try {
                // Parse the first part as an integer
                return Integer.parseInt(parts[0]);
            } catch (NumberFormatException e) {
                // Handle parsing errors
                e.printStackTrace();
            }
        }

        // Default return value if parsing fails
        return 0;
    }

}


