package dto;

import enums.RoomStatus;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;

public class RoomRequestDTO {

    @Column(unique = true, nullable = false)
    @NotBlank(message = "room number can't be blank")
    private String roomNumber;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false)
    private Integer capacity;

    @Column
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
