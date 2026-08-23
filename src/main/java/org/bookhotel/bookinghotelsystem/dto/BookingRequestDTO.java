package org.bookhotel.bookinghotelsystem.dto;

import org.bookhotel.bookinghotelsystem.entity.Room;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;

public class BookingRequestDTO {

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Room room;




    public BookingRequestDTO() {
    }

    public BookingRequestDTO( LocalDateTime startDate, LocalDateTime endDate) {

        this.startDate = startDate;
        this.endDate = endDate;
    }

    public BookingRequestDTO(LocalDateTime startDate, LocalDateTime endDate, Room room) {
        this.startDate = startDate;
        this.endDate = endDate;
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

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
