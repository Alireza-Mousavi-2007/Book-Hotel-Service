package org.bookhotel.bookinghotelsystem.dto;

import lombok.Builder;
import org.bookhotel.bookinghotelsystem.enums.RoomStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
@Builder
public class RoomRequestDTO {

    @NotEmpty(message = "room number can't be blank")
    private String roomNumber;

    @NotNull(message = "room price can't be null")
    private Long price;

    @NotNull(message = "room capacity can't be null")
    private Integer capacity;

    @NotNull(message = "room status can't be null")
    private RoomStatus status;

    public RoomRequestDTO() {
    }

    public RoomRequestDTO(String roomNumber, Integer capacity) {
        this.roomNumber = roomNumber;
        this.capacity = capacity;
    }

    public RoomRequestDTO(String roomNumber, Long price, Integer capacity, RoomStatus status) {
        this.roomNumber = roomNumber;
        this.price = price;
        this.capacity = capacity;
        this.status = status;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public RoomStatus getStatus() {
        return status;
    }

    public void setStatus(RoomStatus status) {
        this.status = status;
    }
}
