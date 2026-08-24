package org.bookhotel.bookinghotelsystem.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.bookhotel.bookinghotelsystem.dto.BookingRequestDTO;
import org.bookhotel.bookinghotelsystem.entity.Booking;
import org.bookhotel.bookinghotelsystem.service.BookingService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Booking")
@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Operation(summary = "getAll")
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @Operation(summary = "getByBookingCode")
    @GetMapping("/{bookingCode}")
    @PreAuthorize("hasAuthority('READ')")
    public BookingRequestDTO getBookingByBookingCode(@Valid @PathVariable String bookingCode) {
        return bookingService.getBookingByBookingCode(bookingCode);
    }

    @Operation(summary = "getById")
    @GetMapping("/admin/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Booking getBookingById(@Valid @PathVariable Integer id) {
        return bookingService.getBookingById(id);
    }

    @Operation(summary = "addBooking")
    @PostMapping
    @PreAuthorize("hasAuthority('CREATE')")
    public Booking addBooking(@Valid @RequestBody BookingRequestDTO bookingRequestDTO) {
        return bookingService.addBooking(bookingRequestDTO);
    }

    @Operation(summary = "cancelByBookingCode")
    @PatchMapping("/{bookingCode}/cancel")
    @PreAuthorize("hasAuthority('CANCEL')")
    public void cancelBookingByBookingCode(@Valid @PathVariable String bookingCode) {
        bookingService.cancelBookingByBookingCode(bookingCode);
    }


}
