package dto;

import entity.Room;
import entity.User;

import java.time.LocalDateTime;

public class BookingRequestDTO {

    private String bookingCode;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Room room;
    private User user;


    public BookingRequestDTO() {
    }

    public BookingRequestDTO(String bookingCode, LocalDateTime startDate, LocalDateTime endDate) {
        this.bookingCode = bookingCode;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public BookingRequestDTO(String bookingCode, LocalDateTime startDate, LocalDateTime endDate, Room room) {
        this.bookingCode = bookingCode;
        this.startDate = startDate;
        this.endDate = endDate;
        this.room = room;
    }

    public BookingRequestDTO(String bookingCode, LocalDateTime startDate, LocalDateTime endDate, Room room, User user) {
        this.bookingCode = bookingCode;
        this.startDate = startDate;
        this.endDate = endDate;
        this.room = room;
        this.user = user;
    }

    public String getBookingCode() {
        return bookingCode;
    }

    public void setBookingCode(String bookingCode) {
        this.bookingCode = bookingCode;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
