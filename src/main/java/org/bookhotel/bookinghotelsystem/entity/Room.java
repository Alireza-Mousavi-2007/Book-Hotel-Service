package org.bookhotel.bookinghotelsystem.entity;

import lombok.Builder;
import org.bookhotel.bookinghotelsystem.enums.RoomStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "rooms")
@Builder
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Integer id;

    @Column(name = "room_number", unique = true, nullable = false)
    @NotBlank(message = "room number can't be blank")
    private String roomNumber;

    @Column(name = "room_price",nullable = false)
    private Long price;

    @Column(name = "room_capacity",nullable = false)
    private Integer capacity;

    @Column(name = "room_status")
    @Enumerated(EnumType.STRING)
    private RoomStatus status;

    public Room() {
    }

    public Room(String roomNumber, Long price, Integer capacity, RoomStatus status) {
        this.roomNumber = roomNumber;
        this.price = price;
        this.capacity = capacity;
        this.status = status;
    }

    public Room(Integer id, String roomNumber, Long price, Integer capacity, RoomStatus status) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.price = price;
        this.capacity = capacity;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
