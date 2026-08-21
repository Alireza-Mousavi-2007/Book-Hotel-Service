package dto;

import entity.Room;
import entity.User;
import enums.BookingStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

public class BookingRequestDTO {

    private User user;

    private Room room;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    private BookingStatus status;

    @Column(nullable = false)
    private LocalDateTime createdAt;


    public BookingRequestDTO() {
    }

    public BookingRequestDTO(User user, Room room) {
        this.user = user;
        this.room = room;
    }

    public BookingRequestDTO(User user, Room room, LocalDateTime startDate, LocalDateTime endDate, BookingStatus status, LocalDateTime createdAt) {
        this.user = user;
        this.room = room;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.createdAt = createdAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
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

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
