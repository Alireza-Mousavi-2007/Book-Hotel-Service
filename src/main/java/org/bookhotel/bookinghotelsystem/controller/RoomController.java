package org.bookhotel.bookinghotelsystem.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.bookhotel.bookinghotelsystem.dto.RoomRequestDTO;
import org.bookhotel.bookinghotelsystem.entity.Room;
import org.bookhotel.bookinghotelsystem.enums.RoomStatus;
import org.bookhotel.bookinghotelsystem.service.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Room")
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
    public ResponseEntity<List<Room>> getAllRoom() {
        var rooms = roomService.getAllRoom();
        return ResponseEntity.status(HttpStatus.FOUND).body(rooms);
    }

    @Operation(summary = "GetById")
    @GetMapping("/admin/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Room> getRoomById(@Valid @PathVariable Integer id) {
        var room=roomService.getRoomById(id);
        return ResponseEntity.status(HttpStatus.FOUND).body(roomService.getRoomById(id));
    }

    @Operation(summary = "GetByRoomNumber")
    @GetMapping("/{roomNumber}")
    @PreAuthorize("hasAuthority('READ')")
    public ResponseEntity<Room> getRoomByRoomNumber(@Valid @PathVariable String roomNumber) {
        var room=roomService.getRoomByRoomNumber(roomNumber);
        return ResponseEntity.status(HttpStatus.FOUND).body(room);
    }

    @Operation(summary = "AddRoomByAdmin")
    @PostMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Room> addRoom(@Valid @RequestBody RoomRequestDTO roomRequestDTO) {
        var room=roomService.addRoom(roomRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(room) ;
    }

    @Operation(summary = "updateRoomStatus")
    @PutMapping("/admin/{roomNumber}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Room> updateRoomStatus(@Valid @PathVariable String roomNumber, @Valid @RequestBody RoomStatus status) {
        var room =roomService.updateRoomStatus(roomNumber, status);
        return  ResponseEntity.status(HttpStatus.OK).body(room);
    }

    @Operation(summary = "deleteByRoomNumber")
    @DeleteMapping("/admin/{roomNumber}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteRoomByRoomNumber(@Valid @PathVariable String roomNumber) {
        roomService.deleteRoomByRoomNumber(roomNumber);
        return ResponseEntity.status(HttpStatus.OK).body("room "+roomNumber+" deleted");
    }
}
