package org.bookhotel.bookinghotelsystem.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.bookhotel.bookinghotelsystem.dto.RoomRequestDTO;
import org.bookhotel.bookinghotelsystem.entity.Room;
import org.bookhotel.bookinghotelsystem.enums.RoomStatus;
import org.bookhotel.bookinghotelsystem.service.RoomService;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name ="Room")
@RestController()
@RequestMapping(path = "/api/rooms", produces = MediaType.APPLICATION_JSON_VALUE)
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @Operation(summary = "getAllRoom")
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Room> getAllRoom() {
        return roomService.getAllRoom();
    }

    @Operation(summary = "GetById")
    @GetMapping("/admin/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Room getRoomById(@Valid @PathVariable Integer id) {
        return roomService.getRoomById(id);
    }

    @Operation(summary = "GetByRoomNumber")
    @GetMapping("/{roomNumber}")
    @PreAuthorize("hasAuthority('READ')")
    public Room getRoomByRoomNumber(@Valid @PathVariable String roomNumber) {
        return roomService.getRoomByRoomNumber(roomNumber);
    }

    @Operation(summary = "AddRoomByAdmin")
    @PostMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public Room addRoom(@Valid @RequestBody RoomRequestDTO roomRequestDTO) {
        return roomService.addRoom(roomRequestDTO);
    }

    @Operation(summary = "updateRoomStatus")
    @PutMapping("/admin/{roomNumber}")
    @PreAuthorize("hasRole('ADMIN')")
    public Room updateRoomStatus(@Valid @PathVariable String roomNumber, @Valid @RequestBody RoomStatus status) {
        return roomService.updateRoomStatus(roomNumber, status);
    }

    @Operation(summary = "deleteByRoomNumber")
    @DeleteMapping("/admin/{roomNumber}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteRoomByRoomNumber(@Valid @PathVariable String roomNumber) {
        roomService.deleteRoomByRoomNumber(roomNumber);
    }
}
