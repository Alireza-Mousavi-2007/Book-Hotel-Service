package org.bookhotel.bookinghotelsystem.controller;


import jakarta.validation.Valid;
import org.bookhotel.bookinghotelsystem.dto.RoomRequestDTO;
import org.bookhotel.bookinghotelsystem.entity.Room;
import org.bookhotel.bookinghotelsystem.enums.RoomStatus;
import org.bookhotel.bookinghotelsystem.service.RoomService;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping(path = "/api/rooms", produces = MediaType.APPLICATION_JSON_VALUE)
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Room> getAllRoom() {
        return roomService.getAllRoom();
    }

    @GetMapping("/admin/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Room getRoomById(@Valid @PathVariable Integer id) {
        return roomService.getRoomById(id);
    }


    @GetMapping("/{roomNumber}")
    @PreAuthorize("hasAuthority('READ')")
    public Room getRoomByRoomNumber(@Valid @PathVariable String roomNumber) {
        return roomService.getRoomByRoomNumber(roomNumber);
    }

    @PostMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public Room addRoom(@Valid @RequestBody RoomRequestDTO roomRequestDTO) {
        return roomService.addRoom(roomRequestDTO);
    }

    @PutMapping("/admin/{roomNumber}")
    @PreAuthorize("hasRole('ADMIN')")
    public Room updateRoomStatus(@Valid @PathVariable String roomNumber, @Valid @RequestBody RoomStatus status) {
        return roomService.updateRoomStatus(roomNumber, status);
    }

    @DeleteMapping("/admin/{roomNumber}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteRoomByRoomNumber(@Valid @PathVariable String roomNumber) {
        roomService.deleteRoomByRoomNumber(roomNumber);
    }
}
