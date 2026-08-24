package org.bookhotel.bookinghotelsystem.controller;

import jakarta.validation.Valid;
import org.bookhotel.bookinghotelsystem.dto.BookingRequestDTO;
import org.bookhotel.bookinghotelsystem.entity.Booking;
import org.bookhotel.bookinghotelsystem.service.BookingService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @GetMapping("/{bookingCode}")
    @PreAuthorize("hasAuthority('READ')")
    public BookingRequestDTO getBookingByBookingCode(@Valid @PathVariable String bookingCode) {
        return bookingService.getBookingByBookingCode(bookingCode);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Booking getBookingById(@Valid @PathVariable Integer id) {
        return bookingService.getBookingById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('create')")
    public Booking addBooking(@Valid @RequestBody BookingRequestDTO bookingRequestDTO) {
        return bookingService.addBooking(bookingRequestDTO);
    }

    @PatchMapping("/{bookingCode}")
    public void cancelBookingByBookingCode(@Valid @PathVariable String bookingCode) {
        bookingService.cancelBookingByBookingCode(bookingCode);
    }


}
