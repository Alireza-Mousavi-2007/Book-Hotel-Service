package org.bookhotel.bookinghotelsystem.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.bookhotel.bookinghotelsystem.dto.BookingRequestDTO;
import org.bookhotel.bookinghotelsystem.entity.Booking;
import org.bookhotel.bookinghotelsystem.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<Booking>> getAllBookings() {
        var bookings = bookingService.getAllBookings();
        return ResponseEntity.status(HttpStatus.FOUND).body(bookings);
    }

    @Operation(summary = "getByBookingCode")
    @GetMapping("/{bookingCode}")
    @PreAuthorize("hasAuthority('READ') or @bookingServiceImpl.getUsernameWithBookingCode(#bookingCode)==authentication.name")
    public ResponseEntity<BookingRequestDTO> getBookingByBookingCode(@Valid @PathVariable String bookingCode) {
        var booking = bookingService.getBookingByBookingCode(bookingCode);
        return ResponseEntity.status(HttpStatus.FOUND).body(booking);
    }

    @Operation(summary = "getById")
    @GetMapping("/admin/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Booking> getBookingById(@Valid @PathVariable Integer id) {
        var booking = bookingService.getBookingById(id);
        return ResponseEntity.status(HttpStatus.FOUND).body(booking);
    }

    @Operation(summary = "addBooking")
    @PostMapping
    @PreAuthorize("hasAuthority('CREATE')")
    public ResponseEntity<Booking> addBooking(@Valid @RequestBody BookingRequestDTO bookingRequestDTO) {
        var booking = bookingService.addBooking(bookingRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(booking);
    }

    @Operation(summary = "cancelByBookingCode")
    @PatchMapping("/{bookingCode}/cancel")
    @PreAuthorize("hasAuthority('CANCEL')")
    public ResponseEntity<String> cancelBookingByBookingCode(@Valid @PathVariable String bookingCode) {
        bookingService.cancelBookingByBookingCode(bookingCode);
       return ResponseEntity.status(HttpStatus.OK).body("booking " + bookingCode + " canceled");
    }
}
