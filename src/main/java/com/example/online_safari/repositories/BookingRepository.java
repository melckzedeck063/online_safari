package com.example.online_safari.repositories;

import com.example.online_safari.model.Bookings;
import com.example.online_safari.model.UserAccount;
import com.example.online_safari.utils.BookingStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Bookings, Long> {

    Optional<Bookings> findFirstByUuid(String uuid);

    Page<Bookings> findAllByCreatedAtIsGreaterThanEqual(LocalDateTime localDateTime, Pageable pageable);


    Page<Bookings>  findAllByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    Page<Bookings> findAllByStatusOrderByCreatedAtDesc(BookingStatus status, Pageable pageable);

    Page<Bookings> findAllByBookedByAndStatusNotOrderByCreatedAtDesc(UserAccount bookedBy, String status, Pageable pageable);

    Page<Bookings> findAllByStatusNotAndBookedByOrderByCreatedAtDesc(String status, UserAccount owner, Pageable pageable);

    Page<Bookings> findAllByDeletedFalseAndStatusNotOrderByCreatedAtDesc(String status,Pageable pageable);

    Integer countAllByStatus(BookingStatus status);

    Long countAllByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

}
