package service.impl;

import dto.BookingRequestDTO;
import entity.Booking;
import enums.BookingStatus;
import exception.RoomNotFoundException;
import repository.BookingRepository;
import service.BookingService;

import java.time.LocalDateTime;
import java.util.List;

public class BookingServiceImpl implements BookingService {

    private final BookingRepository repo;

    public BookingServiceImpl(BookingRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Booking> getAllBookings() {
        return repo.findAll();
    }

    @Override
    public Booking getBookingByBookingCode(String bookingCode) {
        var book = repo.findBookingByBookingCode(bookingCode);
        if (book == null) throw new RoomNotFoundException("there's no room with = " + bookingCode);
        return book;
    }

    @Override
    public Booking getBookingById(Integer id) {
        var book = repo.findById(id);
        if (book.isEmpty()) throw new RoomNotFoundException("there's no room with = " + id);
        return book.get();
    }

    @Override
    public Booking addBooking(BookingRequestDTO bookingRequestDTO) {
        var book = new Booking();
        book.setBookingCode(bookingRequestDTO.getBookingCode());
        book.setStartDate(bookingRequestDTO.getStartDate());
        book.setEndDate(bookingRequestDTO.getEndDate());
        book.setCreatedAt(LocalDateTime.now());
        book.setRoom(bookingRequestDTO.getRoom());
        book.setUser(bookingRequestDTO.getUser());
        book.setStatus(BookingStatus.CONFIRMED);

        return book;
    }

    @Override
    public void cancelBookingById(Integer id) {
        var book = repo.findById(id);
        if (book.isEmpty()) throw new RoomNotFoundException("there's no room with = " + id);
        else
            book.get().setStatus(BookingStatus.CANCELLED);
    }

    @Override
    public void cancelBookingByBookingCode(String bookingCode) {
        var book = repo.findBookingByBookingCode(bookingCode);
        if (book == null) throw new RoomNotFoundException("there's no room with = " + bookingCode);
        else
            book.setStatus(BookingStatus.CANCELLED);
    }
}
