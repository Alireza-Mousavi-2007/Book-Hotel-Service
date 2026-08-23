package service.impl;

import dto.BookingRequestDTO;
import entity.Booking;
import enums.BookingStatus;
import enums.RoomStatus;
import exception.BookingConflictException;
import exception.BookingNotFoundException;
import exception.NoAccessException;
import exception.RoomNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import repository.BookingRepository;
import service.BookingService;
import service.RoomService;
import service.UserService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import java.util.UUID;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository repo;
    private final UserService userService;
    private final RoomService roomService;

    public BookingServiceImpl(BookingRepository repo, UserService userService, RoomService roomService) {
        this.repo = repo;
        this.userService = userService;
        this.roomService = roomService;
    }

    @Override
    public List<Booking> getAllBookings() {
        return repo.findAll();
    }

    @Override
    public Booking getBookingByBookingCode(String bookingCode) {
        var book = repo.findBookingByBookingCode(bookingCode);
        if (book == null) throw new BookingNotFoundException("there's no room with = " + bookingCode);
        return book;
    }

    @Override
    public Booking getBookingById(Integer id) {
        var book = repo.findById(id);
        if (book.isEmpty()) throw new BookingNotFoundException("there's no room with = " + id);
        return book.get();
    }

    @Override
    @Transactional
    public Booking addBooking(BookingRequestDTO bookingRequestDTO) {
        var book = new Booking();
        var room = bookingRequestDTO.getRoom();

        //control time validation
        if (bookingRequestDTO.getStartDate().isAfter(bookingRequestDTO.getEndDate()))
            throw new RuntimeException("startDate can't be after end date");

        // getUserFromAuthentication
        book.setUser(userService.getByUsername(getAuthenticationName()));


        if (repo.existsByRoomAndStatusAndStartDateBeforeAndEndDateAfter(
                room,
                BookingStatus.CONFIRMED,
                bookingRequestDTO.getStartDate(),
                bookingRequestDTO.getEndDate()
        )) throw new BookingConflictException("you can't book this , it's already taken");


        book.setStartDate(bookingRequestDTO.getStartDate());
        book.setEndDate(bookingRequestDTO.getEndDate());

        book.setRoom(roomService.getRoomById(room.getId()));

        roomService.getRoomById(room.getId()).setStatus(RoomStatus.RESERVED);

        book.setStatus(BookingStatus.CONFIRMED);

        book.setCreatedAt(LocalDateTime.now());

        book.setBookingCode(makeBookingCode());

        return repo.save(book);
    }


    @Override
    @Transactional
    public void cancelBookingByBookingCode(String bookingCode) {
        var book = repo.findBookingByBookingCode(bookingCode);
        if (book == null)
            throw new BookingNotFoundException("there's no room with = " + bookingCode);

        if(book.getStatus().equals(BookingStatus.CANCELLED))
            throw new BookingConflictException("booking is already cancelled");

        if (! book.getUser().getUsername().equals(getAuthenticationName()))
            throw new NoAccessException("you don't have access to this part");

        book.setStatus(BookingStatus.CANCELLED);
        repo.save(book);
    }

    @Override
    public String getAuthenticationName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @Override
    public String makeBookingCode() {
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
        String random = UUID.randomUUID().toString().substring(0, 5);

        return "HTL-" + date + "-" + random;
    }


}
